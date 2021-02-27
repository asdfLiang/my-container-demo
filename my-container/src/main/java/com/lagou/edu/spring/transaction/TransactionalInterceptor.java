package com.lagou.edu.spring.transaction;

import com.lagou.edu.spring.factory.annotation.Transactional;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * 事务拦截
 *
 * @author liangzj
 * @date 2021/2/26 23:21
 */
public class TransactionalInterceptor implements MethodInterceptor {

    private TransactionManager transactionManager = new TransactionManager();

    private Object targetObj;

    public TransactionalInterceptor(Object targetObj) {
        this.targetObj = targetObj;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Annotation classTransactional = targetObj.getClass().getAnnotation(Transactional.class);
        // 如果注解在对象上，对所有方法进行事务管理
        if (classTransactional != null) {
            Method[] declaredMethods = targetObj.getClass().getDeclaredMethods();
            boolean isSelfMethod = Arrays.asList(declaredMethods).contains(method);
            // 只拦截本类中的方法，不拦截父类中的方法
            return isSelfMethod ? getTransactionProxyObject(targetObj, method, args) : method.invoke(targetObj, args);
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
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object getTransactionProxyObject(Object obj, Method method, Object[] args)
            throws SQLException, InvocationTargetException, IllegalAccessException {
        try {
            transactionManager.beginTransaction();
            Object invoke = method.invoke(obj, args);
            transactionManager.commitTransaction();
            return invoke;
        } catch (Exception e) {
            transactionManager.rollBack();
            throw e;
        }
    }
}
