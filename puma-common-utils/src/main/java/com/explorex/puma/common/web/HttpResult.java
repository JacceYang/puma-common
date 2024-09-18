package com.explorex.puma.common.web;


import java.io.Serializable;

public class HttpResult implements Serializable {

    private int code;
    private String msg;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


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
