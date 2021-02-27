package com.lagou.edu.pojo;

import com.lagou.edu.service.TransferService;
import com.lagou.transaction.TransactionManager;
import com.lagou.transaction.TransactionalInterceptor;
import com.lagou.factory.support.AnnotationConfigApplicationContext;
import com.lagou.factory.support.ClassPathXmlApplicationContext;
import net.sf.cglib.proxy.Enhancer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author liangzj
 * @date 2021/2/21 20:00
 */
public class LagouBeanTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testIoC() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        LagouBean bean = applicationContext.getBean(LagouBean.class);
        System.out.println(bean);
    }

    @Test
    public void testAnnotation() throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com/lagou/edu");
        TransferService transferService = applicationContext.getBean(TransferService.class);

        transferService.transfer();
    }

    @Test
    public void testCgLibProxy() {
        Enhancer enhancer = new Enhancer();
        TransferService transferService = new TransferService();
        enhancer.setSuperclass(TransferService.class);
        enhancer.setCallback(new TransactionalInterceptor(transferService));
        transferService = (TransferService) enhancer.create();

        transferService.transfer();
    }

    @Test
    public void testGetTransactionManager() throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com/lagou/edu");
        TransactionManager bean = applicationContext.getBean(TransactionManager.class);
        System.out.println(bean);
    }

}