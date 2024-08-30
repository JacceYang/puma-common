package com.explorx.puma.common.web;

public class WebResponseUtils {

    public static WebResponse success() {
        return new WebResponse();
    }

    public static boolean isSuccess(WebResponse response) {

        return response != null && response.getCode() == 0;
    }

    public static WebResponse fail(int code, String msg) {
        WebResponse response = new WebResponse();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }
}
