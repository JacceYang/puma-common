package com.explorex.puma.common.login;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;


public class RedisCache {


    static final  Cache<String,Object> cache=Caffeine.newBuilder()
            .expireAfterWrite(10,TimeUnit.HOURS)
            .maximumSize(10000).build();


    public static LoginUser getCacheObject(String userKey){

        return (LoginUser) cache.getIfPresent(userKey);
    }

    public static void deleteObject(String userKey) {
        cache.invalidate(userKey);
    }

    public static void setCacheObject(String userKey, LoginUser loginUser, int expireTime, TimeUnit minutes) {
        cache.put(userKey,loginUser);
    }
}
