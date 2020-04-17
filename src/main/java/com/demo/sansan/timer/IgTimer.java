package com.demo.sansan.timer;

import com.demo.sansan.bean.IgPost;
import com.demo.sansan.dao.IgPostDao;
import com.demo.sansan.util.Crawler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * <p>ig定时器。</p>
 *
 * 创建日期 2020年03月27日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@Slf4j
@Component
@EnableScheduling
public class IgTimer {

    @Autowired
    private Crawler crawler;
    @Autowired
    private IgPostDao igPostDao;


    @Async
    @Scheduled(cron = "0 0/10 * * * * ")
    public void crawlerExplore() {
        List<IgPost> igPosts = crawler.crawlerExplore();
        if (igPosts == null || igPosts.size() == 0) {
            return;
        }
        //持久帖子
        igPostDao.bulkInsert(igPosts);
        log.info("持久帖子:{}个", igPosts.size());

    }


}
