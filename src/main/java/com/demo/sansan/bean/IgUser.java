

package com.demo.sansan.bean;



/**
 * <p>Instagram 用户对象。</p>
 *
 * 创建日期 2020年03月25日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
public class IgUser {


    private String userId;
    private String userName;//用户名
    private String nickName;//昵称
    private String smallHeadImg;//小图头像
    private String headImg;//大图头像
    private int postCount;//帖子数
    private int followerCount;//粉丝数
    private int following;//关注数
    private String desc;//描述
    private long updateTime;//更新时间

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

    public String getSmallHeadImg() {
        return smallHeadImg;
    }

    public void setSmallHeadImg(String smallHeadImg) {
        this.smallHeadImg = smallHeadImg;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "IgUser{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", smallHeadImg='" + smallHeadImg + '\'' +
                ", headImg='" + headImg + '\'' +
                ", postCount=" + postCount +
                ", followerCount=" + followerCount +
                ", following=" + following +
                ", desc='" + desc + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }


    public IgUser(String userName, String nickName) {
        this.userName = userName;
        this.nickName = nickName;
    }
}
