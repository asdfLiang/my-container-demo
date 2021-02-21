package com.lagou.factory.support;

import com.lagou.utils.Resources;

import java.io.InputStream;

/**
 * 基于注解的容器上下文构建
 *
 * @author liangzj
 * @date 2021/2/21 21:59
 */
public class AnnotationApplicationContext {

    private InputStream configurationStream;

    private DefaultBeanFactory beanFactory;

    public AnnotationApplicationContext() {

    }

}
