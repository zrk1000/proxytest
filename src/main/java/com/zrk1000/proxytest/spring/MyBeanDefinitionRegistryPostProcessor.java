package com.zrk1000.proxytest.spring;

import com.zrk1000.proxytest.service.TestService;
import com.zrk1000.proxytest.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * Created by rongkang on 2017-03-11.
 */

@Component
//@Configuration
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor ,ApplicationContextAware ,PriorityOrdered {

    public static  ApplicationContext applicationContext;
//    private ServiceFactoryBean<?> serviceFactoryBean = new ServiceFactoryBean();
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

//      获取子容器里的bean
//        subWac.getBean(beanName);
//        subWac.
//        AnnotatedScanner scanner = new AnnotatedScanner(registry);
//        scanner.setBeanNameGenerator(new BeanNameGenerator() {
//            @Override
//            public String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry beanDefinitionRegistry) {
//
//                AnnotatedBeanDefinition annotatedDef = (AnnotatedBeanDefinition) beanDefinition;
//                AnnotationMetadata amd = annotatedDef.getMetadata();
//                Set<String> types = amd.getAnnotationTypes();
//                String name = null;
//                for (String type : types) {
//                    System.out.println(type);
//                    AnnotationAttributes attributes = AnnotationAttributes.fromMap(amd.getAnnotationAttributes(type,false));
//                    if (BOLT_ANNOTATION_CLASSNAME.equals(type)) {
//                        //取boltId作为name
//                        name = (String) attributes.get("boltId");
//                        boltNameList.add(name);
//                    }
//                    if (StringUtils.isNoneBlank(name)) {
//                        return name;
//                    }
//                }
//                return null;
//            }
//        });
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
//        DefaultListableBeanFactory defaultListableBeanFactory =
//                (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
//
//        BeanDefinitionBuilder beanDefinitionBuilder1 =
//                BeanDefinitionBuilder
//                        .genericBeanDefinition(serviceFactoryBean.getClass())
//                        .addConstructorArgValue(TestService.class);
//        BeanDefinitionBuilder beanDefinitionBuilder1 =
//                BeanDefinitionBuilder
//                        .genericBeanDefinition(TestService.class);
////        BeanDefinitionBuilder beanDefinitionBuilder2 =
////                BeanDefinitionBuilder
////                        .genericBeanDefinition(serviceFactoryBean.getClass())
////                        .addConstructorArgValue(UserService.class);
//        BeanDefinitionBuilder beanDefinitionBuilder2 =
//                BeanDefinitionBuilder
//                        .genericBeanDefinition(UserService.class);
////        //动态注册bean.
//        registry.registerBeanDefinition("testService",beanDefinitionBuilder1.getBeanDefinition());
//        registry.registerBeanDefinition("userService",beanDefinitionBuilder2.getBeanDefinition());
//
//        beanDefinitionBuilder1.getBeanDefinition().getConstructorArgumentValues().
//                addGenericArgumentValue(beanDefinitionBuilder1.getBeanDefinition().getBeanClassName());
//        beanDefinitionBuilder1.getBeanDefinition().setBeanClass(serviceFactoryBean.getClass());
////        beanDefinitionBuilder1.getBeanDefinition().getConstructorArgumentValues().addIndexedArgumentValue(0, TestService.class);
//
//
//        beanDefinitionBuilder2.getBeanDefinition().getConstructorArgumentValues().
//                addGenericArgumentValue(beanDefinitionBuilder2.getBeanDefinition().getBeanClassName());
//        beanDefinitionBuilder2.getBeanDefinition().setBeanClass(serviceFactoryBean.getClass());
////        beanDefinitionBuilder2.getBeanDefinition().getConstructorArgumentValues().addIndexedArgumentValue(0, UserService.class);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        Map<String, Object> map=beanFactory.getBeansWithAnnotation(DRCPService.class);
//        for (String key : map.keySet()) {
//            System.out.println(map.get(key));
//
//        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    public final static class AnnotatedScanner extends ClassPathBeanDefinitionScanner {
        public AnnotatedScanner(BeanDefinitionRegistry registry) {
            super(registry);
        }

        public void registerDefaultFilters() {
            super.registerDefaultFilters();
//            this.addIncludeFilter(new AnnotationTypeFilter(DRCPService.class));
        }

        public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            return super.isCandidateComponent(beanDefinition);
//                    && beanDefinition.getMetadata().hasAnnotation(DRCPService.class.getName());
        }


    }

}
