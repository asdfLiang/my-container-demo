package com.lagou.factory.support;

import com.lagou.domain.BeanDefinition;
import com.lagou.factory.BeanFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的bean工厂类
 *
 * @author liangzj
 * @date 2021/2/21 18:47
 */
public class DefaultBeanFactory implements BeanFactory {

    // bean信息
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>(256);
    // 单例对象池
    private final Map<String, Object> singletonObjects = new HashMap<>(256);
    // bean的类型和名称映射map
    private final Map<Class, String> beanClassMap = new HashMap<>(256);

    @Override
    public <T> T getBean(Class<T> clazz) {
        return (T) singletonObjects.get(beanClassMap.get(clazz));
    }

    public void instantiateSingletons() throws IllegalAccessException, InstantiationException {
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            singletonObjects.put(entry.getKey(), entry.getValue().getClazz().newInstance());
        }
    }

    /**
     * 注册beanDefinition
     *
     * @param beanName
     * @param beanDefinition
     */
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanClassMap.put(beanDefinition.getClazz(), beanName);
        beanDefinitionMap.put(beanName, beanDefinition);
    }
}
