package com.demo.sansan.service;

import com.demo.sansan.bean.IgPost;
import com.demo.sansan.util.Crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>缓存服务。</p>
 *
 * 创建日期 2020年03月27日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@Service
public class CacheService {

    private Set<IgPost> igPostsFromExplore = new HashSet<>();


    /**
     * 获取来自探索页面的帖子
     * @return
     */
    public Set<IgPost> getIgPostsFromExplore() {
        return igPostsFromExplore;
    }

    /**
     * 设置来自探索页面的帖子
     * @param igPostsFromExplore
     */
    public void setIgPostsFromExplore(Set<IgPost> igPostsFromExplore) {
        if (igPostsFromExplore != null && igPostsFromExplore.size() > 0) {
            this.igPostsFromExplore = igPostsFromExplore;
        }

    }


}
