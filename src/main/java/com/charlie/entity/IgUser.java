package com.charlie.entity;

public class IgUser {
    private String id;

    private String username;

    private String nickname;

    private String headimgpath;

    private String headimgbase64;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getHeadimgpath() {
        return headimgpath;
    }

    public void setHeadimgpath(String headimgpath) {
        this.headimgpath = headimgpath == null ? null : headimgpath.trim();
    }

    public String getHeadimgbase64() {
        return headimgbase64;
    }

    public void setHeadimgbase64(String headimgbase64) {
        this.headimgbase64 = headimgbase64 == null ? null : headimgbase64.trim();
    }
}