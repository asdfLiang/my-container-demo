package com.lagou.edu.spring.factory.annotation;

import com.lagou.edu.spring.factory.support.AnnotationConfigApplicationContext;

/**
 * @author liangzj
 * @date 2021/2/27 20:30
 */
public class WebApplicationContextUtil {

    private static AnnotationConfigApplicationContext applicationContext = null;

    private WebApplicationContextUtil() {
    }

    public static void registerApplicationContext(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        applicationContext = annotationConfigApplicationContext;
    }

    public static AnnotationConfigApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
