package com.zrk1000.proxytest.rpc.drpc;

import com.zrk1000.proxytest.service.TestServiceImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by rongkang on 2017-04-04.
 */
@ConfigurationProperties(prefix="storm.drpc")
public class DrpcAutoconfig {
    private String[] basePackages;

    private String drpcServiceName;

    private Integer spoutNum = 1;

    private Integer BoltNum = 1;

    private Integer workersNum = 1;

    private boolean isDebug = false;

    public String[] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }

    public String getDrpcServiceName() {
        return drpcServiceName;
    }

    public void setDrpcServiceName(String drpcServiceName) {
        this.drpcServiceName = drpcServiceName;
    }

    public Integer getSpoutNum() {
        return spoutNum;
    }

    public void setSpoutNum(Integer spoutNum) {
        this.spoutNum = spoutNum;
    }

    public Integer getBoltNum() {
        return BoltNum;
    }

    public void setBoltNum(Integer boltNum) {
        BoltNum = boltNum;
    }

    public Integer getWorkersNum() {
        return workersNum;
    }

    public void setWorkersNum(Integer workersNum) {
        this.workersNum = workersNum;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }


//    public static void main(String[] args) {
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.zrk1000.proxytest.service","com.zrk1000.proxytest.mybatis");
//        TestServiceImpl beanService = (TestServiceImpl)applicationContext.getBean("testServiceImpl");
//        String say = beanService.say(null, null);
//        System.out.println(DrpcAutoconfig.class.getClassLoader());
//        System.out.println(applicationContext.getClassLoader());
//        System.out.println(applicationContext.getBean("testServiceImpl").getClass().getClassLoader());
//        System.out.println(say);
//    }
}
