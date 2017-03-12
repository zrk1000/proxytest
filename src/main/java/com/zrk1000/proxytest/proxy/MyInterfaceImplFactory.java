package com.zrk1000.proxytest.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by rongkang on 2017-03-11.
 */
public class MyInterfaceImplFactory{

    public static <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(ProxyImpl.class.getClassLoader(), new Class<?>[] {clazz}, new ProxyImpl());
    }


}
