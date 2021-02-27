package com.lagou.edu.pojo;

import com.lagou.edu.service.TransferService;
import com.lagou.edu.spring.transaction.TransactionManager;
import com.lagou.edu.spring.transaction.TransactionalInterceptor;
import com.lagou.edu.spring.factory.support.AnnotationConfigApplicationContext;
import com.lagou.edu.spring.factory.support.ClassPathXmlApplicationContext;
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

        transferService.transfer("6029621011001", "6029621011000", 2.0);
    }

    @Test
    public void testCgLibProxy() {
//        Enhancer enhancer = new Enhancer();
//        TransferService transferService = new TransferService();
//        enhancer.setSuperclass(TransferService.class);
//        enhancer.setCallback(new TransactionalInterceptor(transferService));
//        transferService = (TransferService) enhancer.create();
//
//        transferService.transfer("6029621011001", "6029621011000", 2.0);
    }


}