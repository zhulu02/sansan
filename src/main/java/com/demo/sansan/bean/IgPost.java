

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
    private String link;//帖子连接
    private String smallImg;//小图
    private String userId;//用户ID
    private String userName;//用户名
    private String nickName;//用户昵称
    private String userHead;//用户头像
    private String id;//帖子ID
    private long createTime;//创建时间
    private String img;//大图


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


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    @Override
    public String toString() {
        return "IgPost{" +
                "publishTime=" + publishTime +
                ", text='" + text + '\'' +
                ", link='" + link + '\'' +
                ", smallImg='" + smallImg + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userHead='" + userHead + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public IgPost(String id) {
//        this.id = id;
//    }

    public IgPost() {
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public IgPost(long publishTime, String text, String id) {
        this.publishTime = publishTime;
        this.text = text;
        this.id = id;
        this.link =  "https://www.instagram.com/p/" + id;
    }
}
