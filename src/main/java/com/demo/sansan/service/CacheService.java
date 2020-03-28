package com.demo.sansan.service;

import com.demo.sansan.bean.IgPost;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>缓存服务。</p>
 *
 * 创建日期 2020年03月27日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@Service
public class CacheService {

    private List<IgPost> igPostsFromExplore  = new ArrayList<>();
    private Map<String,String> imgBase64 = new HashMap<>();


    /**
     * 获取来自探索页面的帖子
     * @return
     */
    public List<IgPost> getIgPostsFromExplore() {
        return igPostsFromExplore;
    }

    /**
     * 设置来自探索页面的帖子
     * @param igPostsFromExplore
     */
    public void setIgPostsFromExplore(List<IgPost> igPostsFromExplore) {
        if (igPostsFromExplore != null && igPostsFromExplore.size() > 0) {
            this.igPostsFromExplore = igPostsFromExplore;
        }
    }

    /**
     * 从缓存中获取图片的base64
     * @param key
     * @return
     */
    public String getBase64(String imgUrl){
        return imgBase64.get(imgUrl);
    }

    /**
     * 缓存图片的base64
     * @param imgUrl
     * @param base64
     */
    public void setImgBase64(String imgUrl,String base64 ){
        imgBase64.put(imgUrl,base64);
    }

}
