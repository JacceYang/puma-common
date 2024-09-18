package com.explorex.puma.common;

public enum HttpErrorCodeEum {
    SUCCESS(0, "成功过"),
    ERROR_ILLEGAL_ARGUMENT(-1000, "参数非法"),

    ERROR(-9999, "未知服务异常");

    HttpErrorCodeEum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    private int value;
    private String desc;

}
