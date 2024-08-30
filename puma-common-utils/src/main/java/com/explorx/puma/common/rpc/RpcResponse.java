package com.explorx.puma.common.rpc;

import java.io.Serializable;

public class RpcResponse implements Serializable {

    int code;
    String msg;

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

    public RpcResponse() {
    }
}
