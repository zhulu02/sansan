package com.demo.sansan.util;

import com.demo.sansan.bean.IgPost;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>爬虫。</p>
 *
 * 创建日期 2020年03月27日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
@Slf4j
@Component
public class Crawler {

    @Value("${crawler.proxy.alive}")
    private boolean proxyAlive;

    @Value("${crawler.proxy}")
    private String proxy;

    @Value("${crawler.cookie}")
    private String cookie;


    @Value("${crawler.explore.url}")
    private String exploreAddr;


    /**
     * 采集探索页面
     * @return
     */
    public Set<IgPost> crawlerExplore() {
        String source = null;
        if (proxyAlive) {
            source = crawlerSourceByCookie(exploreAddr, cookie, proxy);
        } else {
            source = crawlerSourceByCookie(exploreAddr, cookie, null);
        }
        if (StringUtils.isBlank(source)) {
            log.warn("探索页面源码采集失败");
            return new HashSet<>();
        }

        log.info("成功采集探索页面源码,源码长度:{}", source.length());
        return parseIgPost(source);
    }


    /**
     * 解析帖子
     * @param source
     * @return
     */
    private Set<IgPost> parseIgPost(String source) {
        Set<IgPost> igPosts = new HashSet<>();
        JsonArray items = new JsonParser().parse(source).getAsJsonObject().getAsJsonArray("sectional_items");
        for (JsonElement item : items) {
            if (item.getAsJsonObject().has("layout_content")) {
                JsonObject layout_content = item.getAsJsonObject().getAsJsonObject("layout_content");
                if (layout_content.has("medias")) {
                    JsonArray medias = layout_content.getAsJsonArray("medias");
                    for (JsonElement media : medias) {
                        try {
                            JsonObject obj = media.getAsJsonObject().getAsJsonObject("media");
                            String media_type = obj.get("media_type").getAsString();
                            if (!"1".equals(media_type)) {
                                continue;
                            }
                            JsonObject user = obj.getAsJsonObject("user");
                            boolean is_private = user.get("is_private").getAsBoolean();
                            String username = user.get("username").getAsString();
                            if (is_private) {
                                log.warn("{}私有", username);
                                continue;
                            }

                            String code = obj.get("code").getAsString();
                            long taken_at = obj.get("taken_at").getAsLong() * 1000; //单位：毫秒
                            JsonArray candidates = obj.getAsJsonObject("image_versions2").getAsJsonArray("candidates");
                            String img = null;
                            for (JsonElement candidate : candidates) {
                                if (candidate.getAsJsonObject().get("height").getAsInt() == 480) {
                                    img = candidate.getAsJsonObject().get("url").getAsString();
                                    break;
                                }
                            }
//                            String img = candidates.get(candidates.size() - 1).getAsJsonObject().get("url").getAsString();
//                            String img = candidates.get(0).getAsJsonObject().get("url").getAsString();
                            String userId = user.get("pk").getAsString();
                            String full_name = user.get("full_name").getAsString();
                            String profile_pic_url = user.get("profile_pic_url").getAsString();
                            String text = obj.getAsJsonObject("caption").get("text").getAsString();
                            String link = "https://www.instagram.com/p/" + code;

                            IgPost igPost = new IgPost();
                            igPost.setPublishTime(taken_at);
                            igPost.setText(text);
                            igPost.setLink(link);
                            igPost.setSmallImg(img);
                            igPost.setUserId(userId);
                            igPost.setNickName(full_name);
                            igPost.setUserHead(profile_pic_url);
                            igPosts.add(igPost);
                            igPosts.add(igPost);
                        } catch (Exception e) {
                            log.warn("解析帖子异常,对象详情:{}", media);
                            log.error("解析帖子异常", e);

                        }
                    }
                }
            }
        }
        return igPosts;
    }

    /**
     * 通过cookie采集源码
     * @param url
     * @param cookie
     * @param proxy
     * @return
     */
    private String crawlerSourceByCookie(String url, String cookie, String proxy) {

        if (StringUtils.isNotBlank(proxy)) {
            String[] proxyArray = proxy.split(":");
            try {
                String json = Jsoup.connect(url).proxy(proxyArray[0], Integer.parseInt(proxyArray[1]))
                        .header("cookie", cookie)
                        .ignoreContentType(true).ignoreHttpErrors(true).execute().body();
                return json;
            } catch (IOException e) {
                log.error("通过代理和cookie采集源码异常,地址:" + url, e);
            }
        } else {
            try {
                String json = Jsoup.connect(url)
                        .header("cookie", cookie)
                        .ignoreContentType(true).ignoreHttpErrors(true).execute().body();
                return json;
            } catch (IOException e) {
                log.error("通过cookie采集源码异常,地址：" + url, e);
            }
        }

        return null;

    }


}
