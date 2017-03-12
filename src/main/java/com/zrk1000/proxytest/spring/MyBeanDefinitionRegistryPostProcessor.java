package com.zrk1000.proxytest.spring;

import com.zrk1000.proxytest.proxy.MyInterfaceImplFactory;
import com.zrk1000.proxytest.service.TestService;
import org.springframework.beans.BeansException;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by rongkang on 2017-03-11.
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor ,ApplicationContextAware ,FactoryBean {

    ApplicationContext applicationContext;
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        TestService _proxy = MyInterfaceImplFactory.getProxy(TestService.class);
//        if(_proxy instanceof TestService){
//            System.out.println("============================");
//        }
//        GenericBeanDefinition definition = new GenericBeanDefinition();
//        definition.setBeanClass(_proxy.getClass());    //设置类
//        definition.setScope("singleton");       //设置scope
//        definition.setLazyInit(false);          //设置是否懒加载
//        definition.setAutowireCandidate(true);  //设置是否可以被其他对象自动注入
//        registry.registerBeanDefinition("testService", definition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //创建bean信息.
//        TestService proxyTestService = MyInterfaceImplFactory.getProxy(TestService.class);
//        Class a = TestService.class;
//        Class<? extends TestService> aClass = proxyTestService.getClass();
//        this.applicationContext = applicationContext;
//        DefaultListableBeanFactory acf = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//        //获取BeanFactory
//        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
//
//        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(aClass);
//        //动态注册bean.
//        defaultListableBeanFactory.registerBeanDefinition("testService",beanDefinitionBuilder.getBeanDefinition());
    }

    @Override
    public Object getObject() throws Exception {
        TestService proxyTestService = MyInterfaceImplFactory.getProxy(TestService.class);
        return proxyTestService;
    }

    @Override
    public Class<?> getObjectType() {
        return TestService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


//    @Bean
//    public TestService getMyInterface() {
//        return MyInterfaceImplFactory.getProxy(TestService.class);
//    }
}
