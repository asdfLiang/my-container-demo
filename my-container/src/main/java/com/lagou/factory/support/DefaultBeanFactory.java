package com.lagou.factory.support;

import com.lagou.domain.BeanDefinition;
import com.lagou.factory.BeanFactory;

import java.lang.reflect.Field;
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

    /**
     * 根据beanDefinition实例化对象
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void instantiateSingletons() throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            // 实例化对象
            Object o = instantiateBeanDefinition(entry.getValue());
            // 放入单例池
            singletonObjects.put(entry.getKey(), o);
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

    /**
     * 根据beanDefinition实例化对象
     *
     * @param beanDefinition
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     */
    private Object instantiateBeanDefinition(BeanDefinition beanDefinition) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Class<?> clazz = beanDefinition.getClazz();
        String[] dependsOn = beanDefinition.getDependsOn();
        Object o = clazz.newInstance();

        if (dependsOn == null || dependsOn.length == 0) {
            return o;
        }

        // 处理依赖的对象
        for (String dependFieldName : dependsOn) {
            Field field = clazz.getDeclaredField(dependFieldName);
            Class<?> fieldType = field.getType();
            // 先从单例池中获取，没有则先创建这个bean
            Object bean = getBean(fieldType);
            if (bean == null) {
                String beanDefinitionName = beanClassMap.get(fieldType);
                BeanDefinition beanDefinition0 = beanDefinitionMap.get(beanDefinitionName);
                Object dependObject = instantiateBeanDefinition(beanDefinition0);
                // 注入依赖对象
                dependInject(o, dependFieldName, dependObject);
            }
        }

        return o;
    }

    /**
     * 依赖对象注入
     *
     * @param targetObject
     * @param dependFieldName
     * @param dependObject
     */
    private void dependInject(Object targetObject, String dependFieldName, Object dependObject) throws NoSuchFieldException, IllegalAccessException {
        Class<?> aClass = targetObject.getClass();
        Field field = aClass.getDeclaredField(dependFieldName);

        boolean accessible = field.isAccessible();
        field.setAccessible(true);

        field.set(targetObject, dependObject);

        field.setAccessible(accessible);
    }


}
