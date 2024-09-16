package com.explorx.puma.common.web;

public class HttpResultUtils {

    public static HttpResult success() {
        return new HttpResult();
    }

    public static HttpResult success(Object data) {
        final HttpResult httpResult = new HttpResult();
        httpResult.setData(data);
        return httpResult;
    }

    public static boolean isSuccess(HttpResult response) {
        return response != null && response.getCode() == 0;
    }

    public static HttpResult fail(int code, String msg) {
        HttpResult response = new HttpResult();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }
}
