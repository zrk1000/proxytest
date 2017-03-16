package com.zrk1000.proxytest.proxy;

import org.springframework.beans.factory.FactoryBean;

/**
 * Created by pc on 2017/3/13.
 */
public class ServiceFactoryBean<T> implements FactoryBean<T> {

    private Class<T> serviceInterface;

    public ServiceFactoryBean(){}
    public ServiceFactoryBean(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public Class<T> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    @Override
    public T getObject() throws Exception {
//        System.out.println("ServiceFactoryBean getObject");
        return ServiceProxyFactory.getProxy(serviceInterface);

    }

    @Override
    public Class<?> getObjectType() {
//        System.out.println("ServiceFactoryBean getObjectType");
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
//        System.out.println("ServiceFactoryBean isSingleton");
        return true;
    }
}
