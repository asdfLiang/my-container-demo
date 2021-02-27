package com.lagou.transaction;

import com.lagou.factory.annotation.Transactional;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 事务拦截
 *
 * @author liangzj
 * @date 2021/2/26 23:21
 */
public class TransactionalInterceptor implements MethodInterceptor {

    private Object targetObj;

    public TransactionalInterceptor(Object targetObj) {
        this.targetObj = targetObj;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
            throws Throwable {
        Annotation classTransactional = targetObj.getClass().getAnnotation(Transactional.class);
        // 如果注解在对象上，对所有方法进行事务管理
        if (classTransactional != null) {
            return getTransactionProxyObject(targetObj, method, args);
        } else {
            // 否则只代理有注解的方法
            Transactional methodTransactional = method.getAnnotation(Transactional.class);
            return (methodTransactional != null) ? getTransactionProxyObject(targetObj, method, args) : method.invoke(targetObj, args);
        }
    }

    /**
     * 获取事务代理对象
     *
     * @param obj
     * @param method
     * @param args
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object getTransactionProxyObject(Object obj, Method method, Object[] args)
            throws Throwable {
        System.out.println(obj.getClass().getName() + "." + method.getName() + "事务开启。。。");
        Object invoke = method.invoke(obj, args);
        System.out.println(obj.getClass().getName() + "." + method.getName() + "事务提交。。。");

        return invoke;
    }
}
