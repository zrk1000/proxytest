package com.zrk1000.proxytest.proxy;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import com.zrk1000.proxytest.rpc.RpcHandle;

/**
 * Created by rongkang on 2017-03-11.
 */
public  class ServiceProxy implements InvocationHandler,Serializable {

    @Autowired
    protected RpcHandle rpcHandle;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //Object 中的方法
        if(Object.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {
                throw new Exception("Object method invoke exception!");
            }
        //业务方法
        } else {
            System.out.println(method.getDeclaringClass().getName()+"--"+method.getName());
            return rpcHandle.exec(proxy, method, args);
        }
    }
}
