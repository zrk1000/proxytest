package com.zrk1000.proxytest.rpc;

import com.zrk1000.proxytest.proxy.ServiceMethod;

import java.io.Closeable;
import java.lang.reflect.Method;

/**
 * Created by zrk-PC on 2017/4/1.
 */
public interface RpcHandle extends Closeable {

    Object exec(ServiceMethod serviceMethod,Object[] args);

}
