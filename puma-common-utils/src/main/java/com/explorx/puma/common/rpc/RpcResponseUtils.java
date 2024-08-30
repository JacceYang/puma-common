package com.explorx.puma.common.rpc;

public class RpcResponseUtils {

    public static RpcResponse success() {
        return new RpcResponse();
    }

    public static boolean isSuccess(RpcResponse response) {
        return response != null && response.code == 0;
    }

    public static RpcResponse fail(int code, String msg) {
        final RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setCode(code);
        rpcResponse.setMsg(msg);
        return rpcResponse;
    }
}
