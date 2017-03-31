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

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

public class ClassPathServiceScanner extends ClassPathBeanDefinitionScanner {

  private Class<? extends Annotation> annotationClass;

  private Class<?> markerInterface;

  private ServiceFactoryBean<?> serviceFactoryBean = new ServiceFactoryBean<Object>();

  public ClassPathServiceScanner(BeanDefinitionRegistry registry) {
    super(registry, false);
  }

  public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
    this.annotationClass = annotationClass;
  }

  public void setMarkerInterface(Class<?> markerInterface) {
    this.markerInterface = markerInterface;
  }

  public void setServiceFactoryBean(ServiceFactoryBean<?> serviceFactoryBean) {
    this.serviceFactoryBean = serviceFactoryBean != null ? serviceFactoryBean : new ServiceFactoryBean<Object>();
  }

  public void registerFilters() {
    boolean acceptAllInterfaces = true;

    // if specified, use the given annotation and / or marker interface
    if (this.annotationClass != null) {
      addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
      acceptAllInterfaces = false;
    }

    // override AssignableTypeFilter to ignore matches on the actual marker interface
    if (this.markerInterface != null) {
      addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
        @Override
        protected boolean matchClassName(String className) {
          return false;
        }
      });
      acceptAllInterfaces = false;
    }

    if (acceptAllInterfaces) {
      // default include filter that accepts all classes
      addIncludeFilter(new TypeFilter() {
        @Override
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
          return true;
        }
      });
    }

    // exclude package-info.java
    addExcludeFilter(new TypeFilter() {
      @Override
      public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        String className = metadataReader.getClassMetadata().getClassName();
        return className.endsWith("package-info");
      }
    });
  }


  @Override
  public Set<BeanDefinitionHolder> doScan(String... basePackages) {
    Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

    if (beanDefinitions.isEmpty()) {
      logger.warn("No MyBatis mapper was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
    } else {
      processBeanDefinitions(beanDefinitions);
    }

    return beanDefinitions;
  }

  private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
    GenericBeanDefinition definition;
    for (BeanDefinitionHolder holder : beanDefinitions) {
      definition = (GenericBeanDefinition) holder.getBeanDefinition();
      if (logger.isDebugEnabled()) {
        logger.debug("Creating ServiceFactoryBean with name '" + holder.getBeanName()
          + "' and '" + definition.getBeanClassName() + "' mapperInterface");
      }
      // the mapper interface is the original class of the bean
      // but, the actual class of the bean is ServiceFactoryBean
      definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName()); // issue #59
      definition.setBeanClass(this.serviceFactoryBean.getClass());
      definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
    }
  }


  @Override
  protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
    return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
  }


  @Override
  protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
    if (super.checkCandidate(beanName, beanDefinition)) {
      return true;
    } else {
      logger.warn("Skipping ServiceFactoryBean with name '" + beanName
          + "' and '" + beanDefinition.getBeanClassName() + "' ServiceInterface"
          + ". Bean already defined with the same name!");
      return false;
    }
  }

}
