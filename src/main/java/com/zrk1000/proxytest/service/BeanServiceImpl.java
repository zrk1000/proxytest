package com.zrk1000.proxytest.service;

import org.springframework.stereotype.Service;

/**
 * Created by rongkang on 2017-04-04.
 */
@Service("beanService")
public class BeanServiceImpl {

    public void doSomeThing(){
        System.out.println("doSomeThing");
    }
}
