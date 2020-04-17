package com.demo.sansan.util;

import com.demo.sansan.bean.IgPost;
import com.demo.sansan.bean.IgUser;
import com.demo.sansan.dao.IgPostDao;
import com.demo.sansan.dao.IgUserDao;
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
import java.util.*;

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
    private IgUserDao igUserDao;

    @Autowired
    private IgPostDao igPostDao;


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
//                            String full_name = user.get("full_name").getAsString();
//                            String profile_pic_url = user.get("profile_pic_url").getAsString();
                            String text = null;
                            try {
                                text = obj.getAsJsonObject("caption").get("text").getAsString();
                            } catch (Exception e) {
                                log.warn("解析文本异常,json = " + obj);
                            }
                            IgPost igPost = new IgPost(taken_at, text, code);
                            String smallImgBase64 = encodeImageToBase64(img, proxy);
                            if (StringUtils.isNotBlank(smallImgBase64)) {
                                igPost.setSmallImg(smallImgBase64);
                                igPost.setUserId(userId);
                                igPost.setUserName(username);
                                igPosts.add(igPost);
                            }

                        } catch (Exception e) {
                            log.warn("解析帖子异常,对象详情:{}", media);
                            log.error("解析帖子异常", e);

                        }
                    }
                }
            }
        }

        return new ArrayList<>(igPosts);
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
            return null;
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
            return null;
        }

        List<IgPost> igPosts = new ArrayList<>();
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
            IgPost igPost = new IgPost(taken_at_timestamp, null, shortcode);

            igPost.setSmallImg(img);//设置小图
            igPost.setUserId(id);
            igPost.setNickName(full_name);
            igPost.setUserName(username);
            igPost.setUserHead(profile_pic_url);//设置用户大图
            igPosts.add(igPost);
        }
        return igPosts;
    }


    /**
     * 爬取内容
     * @param id
     * @return
     */
    public List<Object> crawlerContent(String id) {

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
                    log.error("解析帖子详情失败,帖子地址:" + url, e);
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

        IgPost igPost = new IgPost(taken_at_timestamp, text, id);
//        String displayUrlBase64 =  encodeImageToBase64(display_url, proxy);
        igPost.setImg(display_url);//小图
        igPost.setText(text);

        int count = owner.getAsJsonObject("edge_owner_to_timeline_media").get("count").getAsInt();//帖子数
        IgUser igUser = new IgUser(username, full_name);
//        String userHeadBase64 =  encodeImageToBase64(profile_pic_url, proxy);
        igUser.setSmallHeadImg(profile_pic_url);//图片地址转成base64
        igUser.setPostCount(count);


        List<Object> result = new ArrayList<>();
        result.add(igPost);
        result.add(igUser);

//
//        if(StringUtils.isBlank(userHead)){
//            //设置用户信息
//            int count = owner.getAsJsonObject("edge_owner_to_timeline_media").get("count").getAsInt();//帖子数
//            IgUser igUser = new IgUser(username,full_name);
//            String userHeadBase64 =  encodeImageToBase64(profile_pic_url, proxy);
//            igUser.setSmallHeadImg(userHeadBase64);//图片地址转成base64
//            igUser.setPostCount(count);
//            igUserDao.insert(igUser);//新增用户
//            igPostDao.updateImg(igPost);//更新大图
//            igPost.setUserName(igUser.getUserName());
//            igPost.setUserHead(igUser.getSmallHeadImg());
//            igPost.setNickName(igUser.getNickName());
//            log.info("更新内容，新增用户");
//        }else{
//            igPostDao.updateImg(igPost);//更新大图
//            igPost.setUserName(username);
//            igPost.setUserHead(userHead);
//            igPost.setNickName(full_name);
//            log.info("更新内容，新增用户");
//        }


        return result;

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
    private String encodeImageToBase64(String imgUrl, String proxyStr) {
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
     * 图片地址转成base64
     * @param imgUrl
     * @return
     */
    public String encodeImageToBase64(String imgUrl) {
        return encodeImageToBase64(imgUrl, proxy);
    }


}
