
package com.demo.sansan.controller;


import com.demo.sansan.bean.IgPost;
import com.demo.sansan.bean.IgUser;
import com.demo.sansan.service.CacheService;
import com.demo.sansan.service.IgService;
import com.demo.sansan.util.Crawler;
import com.demo.sansan.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Instagram。</p>
 *
 * 创建日期 2020年03月24日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@Slf4j
@Controller
@RequestMapping("/ig")
public class IGController {


    @Autowired
    private IgService igService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private Crawler crawler;

    /**
     * 探索
     * @param map
     * @return
     */
    @RequestMapping("/explore")
    public String explore(ModelMap map) {
        //设置栏目id
        map.put("column_id", 0);
        List<IgPost> igPosts =  cacheService.getIgPostsFromExplore();

//        String json = fileUtil.getDataFromFile("/Users/zhulu/workspace/demo/sansan/source");
//        List<IgPost> igPosts = new Gson().fromJson(json, new TypeToken<List<IgPost>>() {
//        }.getType());

        List<List<IgPost>> lists = new ArrayList<>();
        int last = 0;
        for (int i = 0; i < igPosts.size(); i += 3) {
            if (i != 0) {
                lists.add(igPosts.subList(last, i));
            }
            last = i;
        }
        log.info("传递{}组帖子给前端",lists.size());
        map.put("lists", lists);
        return "explore";
    }


    /**
     * 关注用户
     * @param map
     * @return
     */
    @RequestMapping("/user")
    public String user(ModelMap map) {
        //设置栏目id
        map.put("column_id", 1);
        List<IgUser> igUsers = igService.queryAll();
        map.addAttribute("igUsers", igUsers);
        return "user";
    }


    @Autowired
    private FileUtil fileUtil;

    /**
     * 访问用户时间线
     * @param username
     * @return
     */
    @RequestMapping("/timeline")
    public String timeline(@RequestParam("username") String username, ModelMap map) {

        List<IgPost> igPosts = crawler.crawlerTimeline(username);
//        String json = fileUtil.getDataFromFile("/Users/zhulu/workspace/demo/sansan/source");
//        List<IgPost> igPosts = new Gson().fromJson(json, new TypeToken<List<IgPost>>() {
//        }.getType());
        IgUser igUser = new IgUser();
        if (igPosts != null && igPosts.size() > 0) {
            IgPost igPost = igPosts.get(0);
            igUser.setUsername(igPost.getUserName());
            igUser.setNickName(igPost.getNickName());
            igUser.setHeadImgBase64(igPost.getUserHead());
        }

        List<List<IgPost>> lists = new ArrayList<>();
        int last = 0;
        for (int i = 0; i < igPosts.size(); i += 3) {
            if (i != 0) {
                lists.add(igPosts.subList(last, i));
            }
            last = i;

        }
        log.info("传递{}组帖子给前端",lists.size());
        map.put("lists", lists);
        map.put("igUser", igUser);
        return "timeline";
    }


    /**
     * 访问帖子详情
     * @return
     */
    @RequestMapping("/content")
    public String content(@RequestParam("id") String id, ModelMap map) {

        log.info("采集帖子:{}",id);
        IgPost igPost = crawler.crawlerContent(id);
        if (igPost != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String timeText = simpleDateFormat.format(new Date(igPost.getPublishTime()));
            map.put("igPost", igPost);
            map.put("timeText", timeText);
        }

        return "content";
    }


}
