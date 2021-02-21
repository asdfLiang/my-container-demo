package com.lagou.factory.support;

import com.lagou.factory.BeanFactory;
import com.lagou.utils.Resources;
import com.lagou.xml.XmlBeanDefinitionReader;

import java.io.InputStream;

/**
 * 基于xml的容器上下文构建
 *
 * @author liangzj
 * @date 2021/2/21 18:51
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

    private InputStream configurationStream;

    private DefaultBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String configLocation) {
        // 读取配置文件
        configurationStream = Resources.getResourceAsStream(configLocation);
        // 创建工厂
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        // 加载beanDefinition
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
        // 进行bean的实例化
        finishBeanFactoryInitialization(beanFactory);
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
        }
    }

    /**
     * 向工厂中加载beanDefinition
     *
     * @param beanFactory
     */
    private void loadBeanDefinitions(DefaultBeanFactory beanFactory) {
        // 为beanFactory创建reader
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.setConfigurationStream(configurationStream);
        // 加载beanDefinition
        beanDefinitionReader.loadBeanDefinitions();
        // 注册beanDefinition
        beanDefinitionReader.registerBeanDefinitions();
    }

}
