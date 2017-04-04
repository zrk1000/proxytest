/**
 *    Copyright 2010-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.zrk1000.proxytest.spring;

import com.zrk1000.proxytest.proxy.ServiceProxyFactory;
import com.zrk1000.proxytest.rpc.RpcHandle;
import org.springframework.beans.factory.FactoryBean;

public class ServiceFactoryBean<T>  implements FactoryBean<T> {

  private Class<T> serviceInterface;

  private RpcHandle rpcHandle;

  public ServiceFactoryBean() {
  }
  
  public ServiceFactoryBean(Class<T> mapperInterface) {
    this.serviceInterface = mapperInterface;
  }

  @Override
  public T getObject() throws Exception {
    System.out.println("*************"+serviceInterface.getCanonicalName());
    return ServiceProxyFactory.newInstance(this.serviceInterface,this.rpcHandle);

  }

  @Override
  public Class<T> getObjectType() {
    return this.serviceInterface;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }


  public void setServiceInterface(Class<T> serviceInterface) {
    this.serviceInterface = serviceInterface;
  }

  public Class<T> getServiceInterface() {
    return serviceInterface;
  }

  public RpcHandle getRpcHandle() {
    return rpcHandle;
  }

  public void setRpcHandle(RpcHandle rpcHandle) {
    this.rpcHandle = rpcHandle;
  }
}
