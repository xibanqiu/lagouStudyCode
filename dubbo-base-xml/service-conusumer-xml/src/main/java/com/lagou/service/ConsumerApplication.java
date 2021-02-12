package com.lagou.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerApplication {
    public static void main(String[] args) {


        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("dubbo-comsumer.xml");

        classPathXmlApplicationContext.start();

        HelloService helloService = classPathXmlApplicationContext.getBean("helloService", HelloService.class);

        String result = helloService.sayHello("world");
        System.out.println("result="+result);


    }


}
