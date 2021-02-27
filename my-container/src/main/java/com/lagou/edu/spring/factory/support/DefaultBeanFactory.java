package com.lagou.edu.spring.factory.support;

import com.lagou.edu.spring.domain.BeanDefinition;
import com.lagou.edu.spring.factory.BeanFactory;
import com.lagou.edu.spring.transaction.TransactionalInterceptor;
import net.sf.cglib.proxy.Enhancer;

import javax.sql.DataSource;
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
        // 数据源类型记录为DataSource
        if (DataSource.class.isAssignableFrom(beanDefinition.getClazz())) {
            beanClassMap.put(DataSource.class, beanName);
            beanDefinitionMap.put(beanName, beanDefinition);
            return;
        }

        beanClassMap.put(beanDefinition.getClazz(), beanName);
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    /**
     * 根据beanDefinition实例化对象
     *
     * @param beanDefinition
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    private Object instantiateBeanDefinition(BeanDefinition beanDefinition)
            throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        Class<?> clazz = beanDefinition.getClazz();
        String[] dependsOn = beanDefinition.getDependsOn();
        // 没有依赖的话直接返回对象
        Object o = clazz.newInstance();
        if (dependsOn == null || dependsOn.length == 0) {
            return getProxyBean(o);
        }
        // 处理依赖的对象，对依赖的对象进行注入
        for (String dependFieldName : dependsOn) {
            Field field = clazz.getDeclaredField(dependFieldName);
            Class<?> fieldType = field.getType();
            // 先从单例池中获取，没有则创建这个bean
            Object bean = getBean(fieldType);
            if (bean == null) {
                String beanDefinitionName = beanClassMap.get(fieldType);
                BeanDefinition beanDefinition0 = beanDefinitionMap.get(beanDefinitionName);
                bean = instantiateBeanDefinition(beanDefinition0);
            }
            // 注入依赖对象
            dependInject(o, dependFieldName, bean);
        }

        return getProxyBean(o);
    }

    /**
     * 获取代理对象
     *
     * @param object
     * @param <T>
     * @return
     */
    private <T> T getProxyBean(Object object) {
        // 如果是对象，生成代理对象
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(object.getClass());
        T t = (T) enhancer.create(object.getClass(), new TransactionalInterceptor(object));
        return t;
    }

    /**
     * 注入依赖对象
     *
     * @param targetObject
     * @param dependFieldName
     * @param dependObject
     */
    private void dependInject(Object targetObject, String dependFieldName, Object dependObject)
            throws NoSuchFieldException, IllegalAccessException {

        Class<?> aClass = targetObject.getClass();
        Field field = aClass.getDeclaredField(dependFieldName);

        boolean accessible = field.isAccessible();
        field.setAccessible(true);

        field.set(targetObject, dependObject);

        field.setAccessible(accessible);
    }


}
