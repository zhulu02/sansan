package com.demo.sansan.util;


import com.demo.sansan.bean.IgPost;
import com.google.gson.*;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import sun.misc.BASE64Encoder;


import java.io.*;
import java.net.*;

/**
 * <p>功能描述,该部分必须以中文句号结尾。</p>
 *
 * 创建日期 2020年03月27日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
public class CrawlerTest {


    @Test
    public void downloadImg() {
        String img = "https://scontent-hkt1-1.cdninstagram.com/v/t51.2885-15/e35/c0.180.1440.1440a/s480x480/90179732_207150737175733_5759616195532866234_n.jpg?_nc_ht=scontent-hkt1-1.cdninstagram.com&_nc_cat=101&_nc_ohc=OzJ39ZfCqOcAX9VoJAf&oh=945cd6ae1245cabba0336899676fa016&oe=5EA8177A&ig_cache_key=MjI2OTcwNTY1OTAwMTk1NDcxMw%3D%3D.2";
        try {
            String base64 = encodeImageToBase64(new URL(img));
            System.out.println();
            System.out.println(base64);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 将网络图片编码为base64
     *
     * @param url
     * @return
     * @throws
     */
    public String encodeImageToBase64(URL url) throws Exception {
        System.out.println("图片的路径为:" + url.toString());
        HttpURLConnection conn = null;
        try {

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.0.101", 1082));
            conn = (HttpURLConnection) url.openConnection(proxy);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            inStream.close();
            byte[] data = outStream.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = "data:image/jpg;base64," + encoder.encode(data);
            return base64;//返回Base64编码过的字节数组字符串
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("图片上传失败,请联系客服!");
        }
    }


    /**
     * 读取文件
     * @param filePath
     * @return
     */
    public static String getDataFromFile(String filePath) {
        StringBuilder fileKeywords = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(filePath)));
            String s = "";
            while ((s = br.readLine()) != null) {
                fileKeywords.append(s).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return fileKeywords.toString();
    }


    @Test
    public void crawlerTimeline() {
        String url = "https://www.instagram.com/katyperry/?a=_1";
        try {
            Document document = Jsoup.connect(url).proxy("192.168.0.101", 1082)
                    .ignoreContentType(true).ignoreHttpErrors(true).execute().parse();
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
            if (jsonObject != null) {
                JsonArray profilePage = jsonObject.getAsJsonObject().getAsJsonObject("entry_data").getAsJsonArray("ProfilePage");
                JsonObject user = profilePage.get(0).getAsJsonObject().getAsJsonObject("graphql").getAsJsonObject("user");
                String full_name = user.get("full_name").getAsString();
                String id = user.get("id").getAsString();
                String profile_pic_url = user.get("profile_pic_url").getAsString();
                String username = user.get("username").getAsString();

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
                    System.out.println(new Gson().toJson(igPost));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void crawlerContent() {
        String url = "https://www.instagram.com/p/B-NhjKHpf8d/?a=_1";
        try {
            Document document = Jsoup.connect(url).proxy("192.168.0.101", 1082)
                    .ignoreContentType(true).ignoreHttpErrors(true).execute().parse();

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
            if (jsonObject != null) {
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
                System.out.println(new Gson().toJson(igPost));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}