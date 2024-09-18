package com.explorex.puma.common.dto;

import java.io.Serializable;

public class UserBaseInfo implements Serializable {

    private Long userId;

    private String avatar;

    private String userName;


    public UserBaseInfo() {
    }

    public UserBaseInfo(Long userId, String avatar, String userName) {
        this.userId = userId;
        this.avatar = avatar;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
