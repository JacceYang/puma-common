package com.explorx.puma.common.dto;

import java.io.Serializable;

public class UserBaseInfo implements Serializable {

    private Long userId;

    private String avatar;

    private String username;

    public UserBaseInfo() {
    }

    public UserBaseInfo(Long userId, String avatar, String username) {
        this.userId = userId;
        this.avatar = avatar;
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
