package com.demo.sansan.util;

import com.demo.sansan.bean.IgPost;
import com.demo.sansan.service.CacheService;
import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Autowired
    private CacheService cacheService;


    /**
     * 采集探索页面
     * @return
     */
    public List<IgPost> crawlerExplore() {
        String source = null;
        if (proxyAlive) {
            source = crawlerSourceByCookie(exploreAddr, cookie, proxy);
        } else {
            source = crawlerSourceByCookie(exploreAddr, cookie, null);
        }
        if (StringUtils.isBlank(source)) {
            log.warn("探索页面源码采集失败");
            return new ArrayList<>();
        }

        log.info("成功采集探索页面源码,源码长度:{}", source.length());
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


                            String userId = user.get("pk").getAsString();
                            String full_name = user.get("full_name").getAsString();
                            String profile_pic_url = user.get("profile_pic_url").getAsString();
                            String text = obj.getAsJsonObject("caption").get("text").getAsString();
                            String link = "https://www.instagram.com/p/" + code;

                            IgPost igPost = new IgPost(code);
                            igPost.setPublishTime(taken_at);
                            igPost.setText(text);
                            igPost.setLink(link);
                            igPost.setSmallImg(img);
                            igPost.setUserId(userId);
                            igPost.setNickName(full_name);
                            igPost.setUserHead(profile_pic_url);
                            igPost.setUserName(username);
                            igPosts.add(igPost);
                        } catch (Exception e) {
                            log.warn("解析帖子异常,对象详情:{}", media);
                            log.error("解析帖子异常", e);

                        }
                    }
                }
            }
        }
        return processImg(igPosts);
    }


    /**
     * 爬取用户时间线
     * @param username
     * @return
     */
    public List<IgPost> crawlerTimeline(String username) {
        String url = "https://www.instagram.com/" + username + "/?a=_1";
        Document document = null;
        if (proxyAlive) {
            document = crawlerSource(url, proxy);
        } else {
            document = crawlerSource(url, null);
        }

        if (document == null) {
            return new ArrayList<>();
        }


        Elements elements = document.select("script");
        JsonObject jsonObject = null;
        for (Element element : elements) {
            String string = element.toString();
            if (string.contains("window._sharedData")) {
                String json = null;
                try {
                    json = string.substring(string.indexOf("{"), string.lastIndexOf("}") + 1);
                    if (StringUtils.isNotBlank(json)) {
                        jsonObject = new JsonParser().parse(json).getAsJsonObject();
                        if (jsonObject != null) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    log.error("提取json异常", e);
                }
            }
        }

        if (jsonObject == null) {
            return new ArrayList<>();
        }

        Set<IgPost> igPosts = new HashSet<>();
        JsonArray profilePage = jsonObject.getAsJsonObject().getAsJsonObject("entry_data").getAsJsonArray("ProfilePage");
        JsonObject user = profilePage.get(0).getAsJsonObject().getAsJsonObject("graphql").getAsJsonObject("user");
        String full_name = user.get("full_name").getAsString();
        String id = user.get("id").getAsString();
        String profile_pic_url = user.get("profile_pic_url").getAsString();

        JsonArray edges = user.getAsJsonObject("edge_owner_to_timeline_media").getAsJsonArray("edges");
        for (JsonElement edge : edges) {
            JsonObject node = edge.getAsJsonObject().getAsJsonObject("node");
            if (!"GraphImage".equals(node.get("__typename").getAsString())) {
                continue;
            }
            String shortcode = node.get("shortcode").getAsString();
            long taken_at_timestamp = node.get("taken_at_timestamp").getAsLong() * 1000;//单位：毫秒
            JsonArray candidates = node.getAsJsonArray("thumbnail_resources");
            String img = null;
            for (JsonElement candidate : candidates) {
                if (candidate.getAsJsonObject().get("config_height").getAsInt() == 480) {
                    img = candidate.getAsJsonObject().get("src").getAsString();
                    break;
                }
            }

            IgPost igPost = new IgPost(shortcode);
            igPost.setPublishTime(taken_at_timestamp);
            igPost.setSmallImg(img);
            igPost.setUserId(id);
            igPost.setNickName(full_name);
            igPost.setUserName(username);
            igPost.setUserHead(profile_pic_url);
            igPosts.add(igPost);
        }
        return processImg(igPosts);
    }


    /**
     * 爬取内容
     * @param id
     * @return
     */
    public IgPost crawlerContent(String id) {
        //采集
        String url = "https://www.instagram.com/p/" + id + "/?a=_1";
        Document document = null;
        if (proxyAlive) {
            document = crawlerSource(url, proxy);
        } else {
            document = crawlerSource(url, null);
        }
        if (document == null) {
            return null;
        }

        //提取json
        Elements elements = document.select("script");
        JsonObject jsonObject = null;
        for (Element element : elements) {
            String string = element.toString();
            if (string.contains("window._sharedData")) {
                String json = null;
                try {
                    json = string.substring(string.indexOf("{"), string.lastIndexOf("}") + 1);
                    if (StringUtils.isNotBlank(json)) {
                        jsonObject = new JsonParser().parse(json).getAsJsonObject();
                        if (jsonObject != null) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (jsonObject == null) {
            return null;
        }

        JsonArray postPage = jsonObject.getAsJsonObject("entry_data").getAsJsonArray("PostPage");
        JsonObject media = postPage.get(0).getAsJsonObject().getAsJsonObject("graphql").getAsJsonObject("shortcode_media");
        String display_url = media.get("display_url").getAsString();
        String text = media.getAsJsonObject("edge_media_to_caption").getAsJsonArray("edges").get(0).getAsJsonObject().getAsJsonObject("node").get("text").getAsString();
        long taken_at_timestamp = media.get("taken_at_timestamp").getAsLong() * 1000;
        JsonObject owner = media.getAsJsonObject("owner");
        String profile_pic_url = owner.get("profile_pic_url").getAsString();
        String username = owner.get("username").getAsString();
        String full_name = owner.get("full_name").getAsString();
        IgPost igPost = new IgPost();
        igPost.setPublishTime(taken_at_timestamp);
        igPost.setSmallImg(display_url);
        igPost.setText(text);
        igPost.setUserHead(profile_pic_url);
        igPost.setUserName(username);
        igPost.setNickName(full_name);
        processImg(igPost);
        return igPost;

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


    /**
     * 采集网页对象
     * @param url
     * @param proxy
     * @return
     */
    private Document crawlerSource(String url, String proxy) {

        if (StringUtils.isNotBlank(proxy)) {
            String[] proxyArray = proxy.split(":");
            try {
                Document document = Jsoup.connect(url).proxy(proxyArray[0], Integer.parseInt(proxyArray[1]))
                        .ignoreContentType(true).ignoreHttpErrors(true).execute().parse();
                return document;
            } catch (IOException e) {
                log.error("通过代理采集源码异常,地址:" + url, e);
            }
        } else {
            try {
                Document document = Jsoup.connect(url)
                        .ignoreContentType(true).ignoreHttpErrors(true).execute().parse();
                return document;
            } catch (IOException e) {
                log.error("通过cookie采集源码异常,地址：" + url, e);
            }
        }
        return null;
    }

    /**
     * 将网络图片转换成base64
     * @param imgUrl
     * @param proxyStr
     * @return
     */
    public String encodeImageToBase64(String imgUrl, String proxyStr) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(imgUrl);
            //设置代理
            if (proxyAlive) {
                String[] proxyArray = proxyStr.split(":");
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyArray[0], Integer.parseInt(proxyArray[1])));
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
            conn.setRequestMethod("GET");//设置请求方式为"GET"
            conn.setConnectTimeout(5 * 1000);//超时响应时间为5秒
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();//得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] buffer = new byte[1024];//创建一个Buffer字符串
            int len = 0;//每次读取的字符串长度，如果为-1，代表全部读取完毕
            while ((len = inStream.read(buffer)) != -1) {//使用一个输入流从buffer里把数据读取出来
                outStream.write(buffer, 0, len);//用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            }
            inStream.close();//关闭输入流
            byte[] data = outStream.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();//对字节数组Base64编码
            String base64 = "data:image/jpg;base64," + encoder.encode(data);
            return base64;//返回Base64编码过的字节数组字符串
        } catch (Exception e) {
            log.error("图片处理异常", e);
        }
        return null;
    }


    /**
     * 处理图片，将其转换成base64
     * @param igPosts
     * @return
     */
    public List<IgPost> processImg(Set<IgPost> igPosts) {

        List<IgPost> igPostList = new ArrayList<>();
        for (IgPost igPost : igPosts) {
            //获取帖子小图的base64
            String smallImg = igPost.getSmallImg();
            String smallImgBase64 = cacheService.getBase64(smallImg);
            if (StringUtils.isBlank(smallImgBase64)) {
                smallImgBase64 = encodeImageToBase64(smallImg, proxy);
            }
            if (StringUtils.isNotBlank(smallImgBase64)) {
                cacheService.setImgBase64(smallImg, smallImgBase64);//TODO 添加到缓存
                igPost.setSmallImg(smallImgBase64);
            }
            //获取用户头像的base64
            String userHead = igPost.getUserHead();
            String userHeadBase64 = cacheService.getBase64(userHead);
            if (StringUtils.isBlank(userHeadBase64)) {
                userHeadBase64 = encodeImageToBase64(userHead, proxy);
            }
            if (StringUtils.isNotBlank(userHeadBase64)) {
                cacheService.setImgBase64(userHead, userHeadBase64);//TODO 添加到缓存
                igPost.setUserHead(userHeadBase64);
            }
            igPostList.add(igPost);
        }
        return igPostList;
    }

    /**
     * 处理图片
     * @param igPost
     */
    private void processImg(IgPost igPost){
        //获取帖子小图的base64
        String smallImg = igPost.getSmallImg();
        String smallImgBase64 = cacheService.getBase64(smallImg);
        if (StringUtils.isBlank(smallImgBase64)) {
            smallImgBase64 = encodeImageToBase64(smallImg, proxy);
        }
        if (StringUtils.isNotBlank(smallImgBase64)) {
            cacheService.setImgBase64(smallImg, smallImgBase64);//TODO 添加到缓存
            igPost.setSmallImg(smallImgBase64);
        }
        //获取用户头像的base64
        String userHead = igPost.getUserHead();
        String userHeadBase64 = cacheService.getBase64(userHead);
        if (StringUtils.isBlank(userHeadBase64)) {
            userHeadBase64 = encodeImageToBase64(userHead, proxy);
        }
        if (StringUtils.isNotBlank(userHeadBase64)) {
            cacheService.setImgBase64(userHead, userHeadBase64);//TODO 添加到缓存
            igPost.setUserHead(userHeadBase64);
        }
    }

}
