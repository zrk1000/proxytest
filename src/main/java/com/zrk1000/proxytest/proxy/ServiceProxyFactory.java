package com.zrk1000.proxytest.proxy;

import com.zrk1000.proxytest.rpc.RpcHandle;

import java.lang.reflect.Proxy;

/**
 * Created by rongkang on 2017-03-11.
 */
public class ServiceProxyFactory {

    public static <T> T newInstance(Class<T> clazz, RpcHandle rpcHandle) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] {clazz}, new ServiceProxy(rpcHandle,clazz));
    }


}
