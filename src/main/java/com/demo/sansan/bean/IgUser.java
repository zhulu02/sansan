

package com.demo.sansan.bean;


/**
 * <p>Instagram 用户对象。</p>
 *
 * 创建日期 2020年03月25日
 * @author Lu Zhu(zhulu@eefung.com)
 * @since $version$
 */
public class IgUser {


    private String id;
    private String username;//用户名
    private String nickName;//昵称
    private String headImgPath;//头像地址
    private String headImgBase64;//头像


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImgPath() {
        return headImgPath;
    }

    public void setHeadImgPath(String headImgPath) {
        this.headImgPath = headImgPath;
    }

    public String getHeadImgBase64() {
        return headImgBase64;
    }

    public void setHeadImgBase64(String headImgBase64) {
        this.headImgBase64 = headImgBase64;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IgUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", nickName='" + nickName + '\'' +
                ", headImgPath='" + headImgPath + '\'' +
                ", headImgBase64='" + headImgBase64 + '\'' +
                '}';
    }
}
