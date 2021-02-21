package com.lagou.edu.pojo;

import com.lagou.factory.support.ClassPathXmlApplicationContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
}