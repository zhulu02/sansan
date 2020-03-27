

package com.demo.sansan.bean;

/**
 * <p> Instagram 帖子对象。</p>
 *
 * 创建日期 2020年03月24日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
public class IgPost {

    private long publishTime;//发布时间
    private String text;//帖子
    private String base64Img;//图片的base64
    private String imgPath;//图片地址

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBase64Img() {
        return base64Img;
    }

    public void setBase64Img(String base64Img) {
        this.base64Img = base64Img;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "IgPost{" +
                "publishTime=" + publishTime +
                ", text='" + text + '\'' +
                ", base64Img='" + base64Img + '\'' +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
