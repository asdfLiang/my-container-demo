package com.lagou.xml;

import com.lagou.domain.BeanDefinition;
import com.lagou.factory.support.DefaultBeanFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * 负责配置文件解析，将beanDefinition注册到beanFactory中
 *
 * @author liangzj
 * @date 2021/2/21 18:49
 */
public class XmlBeanDefinitionReader {

    private InputStream configurationStream;

    private DefaultBeanFactory beanFactory;

    private final List<BeanDefinition> beanDefinitions = new LinkedList<>();

    public XmlBeanDefinitionReader(DefaultBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 加载bean
     */
    public void loadBeanDefinitions() {
        try {
            // 加载配置文件
            Document read = new SAXReader().read(configurationStream);
            // beans
            Element beansElement = read.getRootElement();
            // 解析beanDefinition
            parseBeanDefinitions(beansElement);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册beanDefinition
     */
    public void registerBeanDefinitions() {
        beanDefinitions.forEach(beanDefinition ->
                beanFactory.registerBeanDefinition(beanDefinition.getId(), beanDefinition)
        );
    }

    public void setConfigurationStream(InputStream configurationStream) {
        this.configurationStream = configurationStream;
    }

    /**
     * 解析beanDefinition
     *
     * @param beansElement
     */
    private void parseBeanDefinitions(Element beansElement) {
        List<Element> beanElements = beansElement.selectNodes("//bean");
        beanElements.forEach(beanElement -> {
            String beanName = beanElement.attributeValue("id");
            String beanClassName = beanElement.attributeValue("class");
            Class<?> beanClass = null;
            try {
                beanClass = Class.forName(beanClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setId(beanName);
            beanDefinition.setClazz(beanClass);

            beanDefinitions.add(beanDefinition);
        });
    }
}
