package com.explorex.puma.common.login;

import lombok.Builder;
import lombok.Data;


@Builder
public class User {
    private Long uid;
    private String nickName;
    private long createTime;


    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public User(Long uid, String nickName, long createTime) {
        this.nickName = nickName;
        this.createTime = createTime;
        this.uid=uid;
    }
}
