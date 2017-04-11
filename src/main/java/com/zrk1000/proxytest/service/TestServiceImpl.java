package com.zrk1000.proxytest.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by rongkang on 2017-03-11.
 */
@Service("myTestService")
public class TestServiceImpl implements TestService{

    public String say(String name, Map<String, Object> map) {

        return "say hello!";
    }

}
