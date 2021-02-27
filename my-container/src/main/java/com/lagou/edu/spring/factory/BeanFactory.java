package com.lagou.edu.spring.factory;

/**
 * bean工厂接口
 *
 * @author liangzj
 * @date 2021/2/21 18:44
 */
public interface BeanFactory {

    /**
     * 获取bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getBean(Class<T> clazz);

}
