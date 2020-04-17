package com.demo.sansan.service;

import com.demo.sansan.bean.IgPost;
import com.demo.sansan.bean.IgUser;
import com.demo.sansan.dao.IgPostDao;
import com.demo.sansan.dao.IgUserDao;
import com.demo.sansan.util.Crawler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;


import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>Instagram 数据处理类。</p>
 *
 * 创建日期 2020年03月25日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@Slf4j
@Service
public class IgService {


    @Autowired
    private Crawler crawler;

    @Autowired
    private IgPostDao igPostDao;
    @Autowired
    private IgUserDao igUserDao;


    /**
     * 查询最新的来自探索页面的帖子
     * @return
     */
    public List<List<IgPost>> explore() {
        List<IgPost> igPosts = igPostDao.queryLatest();
        List<List<IgPost>> result = new ArrayList<>();
        int last = 0;
        for (int i = 0; i <= igPosts.size(); i += 3) {
            if (i != 0) {
                result.add(igPosts.subList(last, i));
            }
            last = i;
        }
        return result;
    }

    /**
     * 时间线
     * @param username
     * @param map
     */
    public void timeline(String username, ModelMap map) {

        List<IgPost> cachePostList = igPostDao.queryByUserName(username);//获取持久的帖子
        Map<String, IgPost> cachePostMap = new HashMap<>();
        for (IgPost igPost : cachePostList) {
            cachePostMap.put(igPost.getId(), igPost);
        }

        List<IgPost> collectPostList = crawler.crawlerTimeline(username);//获取实时爬取的帖子
        //处理小图
        List<IgPost> lastedPost = new ArrayList<>();
        for (IgPost igPost : collectPostList) {
            String key = igPost.getId();
            if (cachePostMap.containsKey(key)) {//设置小图
                igPost.setSmallImg(cachePostMap.get(key).getSmallImg());
            } else {
                String smallImg = igPost.getSmallImg();
                String smallImgBase64 = crawler.encodeImageToBase64(smallImg);
                if (StringUtils.isNotBlank(smallImgBase64)) {
                    log.info("{} 转base64:{}", smallImg, smallImgBase64.length());
                    igPost.setSmallImg(smallImgBase64);
                    lastedPost.add(igPost);
                }
            }
        }
        IgPost igPost = cachePostList.get(0);
        IgUser igUser = new IgUser(igPost.getUserName(), igPost.getNickName());
        if (StringUtils.isBlank(igPost.getUserHead())) {
            String userHead = collectPostList.get(0).getUserHead();
            String userHeadBase64 = crawler.encodeImageToBase64(userHead);
            igUser.setHeadImg(userHeadBase64);
            //TODO 持久到数据库
            igUserDao.update(igUser);
            log.info("{} 转base64:{}，并持久", userHead, userHeadBase64.length());
        } else {
            igUser.setHeadImg(igPost.getUserHead());
        }

        List<List<IgPost>> lists = new ArrayList<>();
        int last = 0;
        for (int i = 0; i <= collectPostList.size(); i += 3) {
            if (i != 0) {
                lists.add(collectPostList.subList(last, i));
            }
            last = i;
        }

        if (lastedPost != null & lastedPost.size() > 0) {
            igPostDao.bulkInsert(lastedPost);
            log.info("持久帖子 ：{}", lastedPost.size());
        }

        log.info("传递{}组帖子给前端", lists.size());


        map.put("lists", lists);
        map.put("igUser", igUser);
    }

    /**
     * 内容页
     * @param id
     * @param map
     */
    public void content(String id, ModelMap map) {

        IgPost igPost = igPostDao.queryById(id);
        String userHead = igPost != null ? igPost.getUserHead() : null;//获取用户头像
        if (igPost == null || StringUtils.isBlank(userHead) || StringUtils.isBlank(igPost.getImg())
                || StringUtils.isBlank(igPost.getText())) {
            List<Object> result = crawler.crawlerContent(id);
            if (result != null && result.size() == 2) {
                IgPost collectPost = (IgPost) result.get(0);
                IgUser igUser = (IgUser) result.get(1);
                if (igUser != null && StringUtils.isBlank(userHead)) {//用户头像为空，新增用户
                    String userHeadBase64 = crawler.encodeImageToBase64(igUser.getSmallHeadImg());
                    igUser.setSmallHeadImg(userHeadBase64);//图片地址转成base64
                    igUserDao.insert(igUser);
                    userHead = userHeadBase64;
                    log.info("插入用户");
                }

                String imgBase64 = crawler.encodeImageToBase64(collectPost.getImg());
                collectPost.setImg(imgBase64);//小图
                collectPost.setUserName(igUser.getUserName());
                collectPost.setUserHead(userHead);
                collectPost.setNickName(igUser.getNickName());
                igPostDao.updateImg(collectPost);
                igPost = collectPost;
                log.info("更新帖子");
            }
        }
        if (igPost != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String timeText = simpleDateFormat.format(new Date(igPost.getPublishTime()));
            map.put("igPost", igPost);
            map.put("timeText", timeText);
        }
    }


}
