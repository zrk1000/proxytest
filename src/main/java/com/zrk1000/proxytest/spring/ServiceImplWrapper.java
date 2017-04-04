//package com.zrk1000.proxytest.spring;
//
//import com.zrk1000.proxytest.proxy.ServiceMethod;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Created by rongkang on 2017-04-04.
// */
//public class ServiceImplWrapper<T> {
//
//    private Class<T> service;
//
//    private T serviceBean;
//
//    private Class<?> serviceInterface;
//
//
//    private final Map<Integer, Method> methodCache = new ConcurrentHashMap<Integer, Method>();
//
//    public Object execute(Method method, Object... params) throws InvocationTargetException, IllegalAccessException {
//        return method.invoke(this,params);
//    }
//
////    private ServiceMethod cachedServiceImplMethod(Integer methodHashCode) {
////        Method method = this.methodCache.get(methodHashCode);
////        if(method == null) {
////            ServiceMethod serviceMethod = new ServiceMethod( this.serviceInterface, method);
////            this.methodCache.put(method, serviceMethod);
////        }
////        return serviceMethod;
////    }
//
//
//    public ServiceImplWrapper(Class<T> service) {
//        this.service = service;
//        this.serviceInterface = service.getClass().getInterfaces()[0];
//        try {
//            this.serviceBean = service.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//}
