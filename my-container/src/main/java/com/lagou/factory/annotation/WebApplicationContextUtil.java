package com.lagou.factory.annotation;

import com.lagou.factory.support.AnnotationConfigApplicationContext;

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
        System.out.println("register");
    }

    public static AnnotationConfigApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
