package com.zrk1000.proxytest.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by rongkang on 2017-03-11.
 */
public class ServiceProxyFactory {

    public static <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] {clazz}, new ServiceProxy());
    }


}
