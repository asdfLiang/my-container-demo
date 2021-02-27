package com.lagou.edu.listener;

import com.lagou.edu.spring.factory.annotation.WebApplicationContextUtil;
import com.lagou.edu.spring.factory.support.AnnotationConfigApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 容器启动监听器
 *
 * @author liangzj
 * @date 2021/2/27 20:14
 */
public class WebApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String basePackage = sce.getServletContext().getInitParameter("basePackage");
        new AnnotationConfigApplicationContext(basePackage);

        System.out.println("。。。。。。。。applicationContext 初始化。。。。。。。。。");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
