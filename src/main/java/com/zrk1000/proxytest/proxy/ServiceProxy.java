package com.zrk1000.proxytest.proxy;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.reflection.ExceptionUtil;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by rongkang on 2017-03-11.
 */
public  class ServiceProxy<T> implements InvocationHandler,Serializable {

//    private final Class<T> serviceInterface;
//
//    public ServiceProxy(Class<T> serviceInterface) {
//        this.serviceInterface = serviceInterface;
//    }

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
            System.out.println("执行"+method.getName()+"方法");
            System.out.println(method.getDeclaringClass().getName());
            return null;
        }
    }
}
