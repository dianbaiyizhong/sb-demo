package com.nntk.restplus.aop;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.nntk.restplus.annotation.RestPlus;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RestPlusConfigScanner implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        run(registry);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }


    public void run(BeanDefinitionRegistry registry) {

        Set<Class<?>> scanPackage = ClassUtil.scanPackageByAnnotation("com.nntk.sb", RestPlus.class);

        for (Class<?> cls : scanPackage) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(cls);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
            definition.setBeanClass(RestPlusAopProxyFactory.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            String beanName = StrUtil.removePreAndLowerFirst(cls.getSimpleName(), 0) + "RestClient";
            registry.registerBeanDefinition(beanName, definition);
        }
    }

}
