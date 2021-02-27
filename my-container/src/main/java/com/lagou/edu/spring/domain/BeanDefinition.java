package com.lagou.edu.spring.domain;

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

    /**
     * 依赖的bean名称
     */
    private String[] dependsOn;


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

    public String[] getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String[] dependsOn) {
        this.dependsOn = dependsOn;
    }
}
