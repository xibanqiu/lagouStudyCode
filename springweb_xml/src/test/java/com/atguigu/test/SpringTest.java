package com.atguigu.test;

import com.lagou.service.TransferService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    @Test
    public void test() throws Exception {

        ClassPathXmlApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        TransferService transferService = (TransferService)application.getBean("transferService");

        transferService.transfer("6029621011000","6029621011001",100);
    }


}
