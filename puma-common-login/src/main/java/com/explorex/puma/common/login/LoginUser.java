package com.explorex.puma.common.login;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

@Builder
@Data
public class LoginUser implements Serializable {

    private String token;

    private String userName;

    private String loginLocation;

    private String browser;

    private String os;

    private long expireTime;

    private long loginTime;

    private String ipaddr;

    private  User user;
}
