package com.explorex.puma.common.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Long uid;
    private String nickName;
    private long createTime;

    public User(Long uid, String nickName, long createTime) {
        this.nickName = nickName;
        this.createTime = createTime;
        this.uid=uid;
    }
}
