package com.atguigu.test;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    @Test
    public void test(){

        ClassPathXmlApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object transferService = application.getBean("transferService");

        System.out.println(transferService);
    }


}
