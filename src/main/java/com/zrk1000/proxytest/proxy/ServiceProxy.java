package com.zrk1000.proxytest.proxy;

import org.apache.ibatis.binding.MapperMethod;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zrk1000.proxytest.rpc.RpcHandle;

/**
 * Created by rongkang on 2017-03-11.
 */
public  class ServiceProxy<T> implements InvocationHandler,Serializable {

    private final RpcHandle rpcHandle;

    private final Class<T> serviceInterface;

    private final  Map<Method, ServiceMethod> methodCache = new ConcurrentHashMap<Method, ServiceMethod>();

    public ServiceProxy(RpcHandle rpcHandle, Class<T> serviceInterface) {
        this.rpcHandle = rpcHandle;
        this.serviceInterface = serviceInterface;
    }

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
            ServiceMethod serviceMethod = cachedServiceMethod(method);
            return rpcHandle.exec(serviceMethod,args);
        }
    }

    private ServiceMethod cachedServiceMethod(Method method) {
        ServiceMethod serviceMethod = (ServiceMethod)this.methodCache.get(method);
        if(serviceMethod == null) {
            serviceMethod = new ServiceMethod(this.serviceInterface, method);
            this.methodCache.put(method, serviceMethod);
        }
        return serviceMethod;
    }
}
