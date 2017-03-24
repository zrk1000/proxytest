package com.zrk1000.proxytest.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by rongkang on 2017-03-11.
 */
public  class ProxyImpl implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("=== "+proxy.getClass()+"==="+method.getName()+"==="+args.toString());
        // do something
        return proxy.getClass().toString();
    }
}
