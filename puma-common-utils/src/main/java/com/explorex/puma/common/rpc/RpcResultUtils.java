package com.explorex.puma.common.rpc;

public class RpcResultUtils {

    public static RpcResult success() {
        return new RpcResult();
    }

    public static RpcResult success(Object data) {
        final RpcResult rpcResult = new RpcResult();
        rpcResult.setData(data);
        return rpcResult;
    }

    public static boolean isSuccess(RpcResult response) {
        return response != null && response.code == 0;
    }

    public static RpcResult fail(int code, String msg) {
        final RpcResult rpcResult = new RpcResult();
        rpcResult.setCode(code);
        rpcResult.setMsg(msg);
        return rpcResult;
    }
}
