package com.lagou.domain;

/**
 * bean的配置信息类
 *
 * @author liangzj
 * @date 2021/2/21 18:42
 */
public class BeanDefinition {
    /**
     * bean的id
     */
    private String id;

    /**
     * bean的类型
     */
    private Class<?> clazz;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
