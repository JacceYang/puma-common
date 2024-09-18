package com.explorex.puma.common.login;


public class UserContextHolder {

    static final ThreadLocal<LoginUser> userInfo=new ThreadLocal<>();


    public  static  LoginUser getUserInfo(){
        return userInfo.get();
    }

    public static void setUserInfo(LoginUser loginUser){
        userInfo.set(loginUser);
    }

    public static  void removeUserInfo(){
        userInfo.remove();;
    }
}
