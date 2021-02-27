package com.lagou.edu.spring.factory.support;

import com.lagou.edu.spring.factory.BeanFactory;
import com.lagou.edu.spring.factory.annotation.AnnotationBeanDefinitionReader;
import com.lagou.edu.spring.factory.annotation.WebApplicationContextUtil;

/**
 * 基于注解的容器上下文构建
 *
 * @author liangzj
 * @date 2021/2/21 21:59
 */
public class AnnotationConfigApplicationContext implements BeanFactory {

    private String basePackage;

    private DefaultBeanFactory beanFactory;

    public AnnotationConfigApplicationContext(String basePackage) {
        this.basePackage = basePackage;
        // 创建工厂
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        // 加载beanDefinition
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
        // 进行bean的实例化
        finishBeanFactoryInitialization(beanFactory);
        //
        WebApplicationContextUtil.registerApplicationContext(this);
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    /**
     * 该方法主要完成bean的实例化
     *
     * @param beanFactory
     */
    private void finishBeanFactoryInitialization(DefaultBeanFactory beanFactory) {
        try {
            beanFactory.instantiateSingletons();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向工厂中加载beanDefinition
     *
     * @param beanFactory
     */
    private void loadBeanDefinitions(DefaultBeanFactory beanFactory) {
        // 为beanFactory创建reader
        AnnotationBeanDefinitionReader beanDefinitionReader = new AnnotationBeanDefinitionReader(beanFactory);
        beanDefinitionReader.setScanPath(basePackage);
        // 加载beanDefinition
        beanDefinitionReader.loadBeanDefinitions();
        // 注册beanDefinition
        beanDefinitionReader.registerBeanDefinitions();
    }
}
