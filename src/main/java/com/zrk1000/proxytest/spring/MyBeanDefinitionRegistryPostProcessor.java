package com.zrk1000.proxytest.spring;

import com.zrk1000.proxytest.annotation.DRCPService;
import com.zrk1000.proxytest.proxy.ServiceFactoryBean;
import com.zrk1000.proxytest.service.TestService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * Created by rongkang on 2017-03-11.
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor ,ApplicationContextAware {

    private ApplicationContext applicationContext;
    private ServiceFactoryBean<?> serviceFactoryBean = new ServiceFactoryBean();
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        AnnotatedScanner scanner = new AnnotatedScanner(registry);
        scanner.setBeanNameGenerator(new BeanNameGenerator() {
            @Override
            public String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry beanDefinitionRegistry) {

                AnnotatedBeanDefinition annotatedDef = (AnnotatedBeanDefinition) beanDefinition;
                AnnotationMetadata amd = annotatedDef.getMetadata();
                Set<String> types = amd.getAnnotationTypes();
                String name = null;
                for (String type : types) {
                    System.out.println(type);
                    AnnotationAttributes attributes = AnnotationAttributes.fromMap(amd.getAnnotationAttributes(type,false));
//                    if (BOLT_ANNOTATION_CLASSNAME.equals(type)) {
//                        //取boltId作为name
//                        name = (String) attributes.get("boltId");
//                        boltNameList.add(name);
//                    }
//                    if (StringUtils.isNoneBlank(name)) {
//                        return name;
//                    }
                }
                return null;
            }
        });
//        if(_proxy instanceof TestService){
//            System.out.println("============================");
//        }
//        GenericBeanDefinition definition = new GenericBeanDefinition();
//        definition.setBeanClass(_proxy.getClass());    //设置类
//        definition.setScope("singleton");       //设置scope
//        definition.setLazyInit(false);          //设置是否懒加载
//        definition.setAutowireCandidate(true);  //设置是否可以被其他对象自动注入
//        registry.registerBeanDefinition("testService", definition);

        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory =
                (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();

        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(serviceFactoryBean.getClass()).addConstructorArgValue(TestService.class);
        //动态注册bean.
        defaultListableBeanFactory.registerBeanDefinition("testServiceFactoryBean",beanDefinitionBuilder.getBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, Object> map=beanFactory.getBeansWithAnnotation(DRCPService.class);
        for (String key : map.keySet()) {
            System.out.println(map.get(key));;

        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public final static class AnnotatedScanner extends ClassPathBeanDefinitionScanner {
        public AnnotatedScanner(BeanDefinitionRegistry registry) {
            super(registry);
        }

        public void registerDefaultFilters() {
            super.registerDefaultFilters();
            this.addIncludeFilter(new AnnotationTypeFilter(DRCPService.class));
        }

        public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            return super.isCandidateComponent(beanDefinition) && beanDefinition.getMetadata().hasAnnotation(DRCPService.class.getName());
        }


    }

}
