package com.lagou;

import com.lagou.bean.ComsumerComponet;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

public class AnnotationConsumerMain {

    public static void main(String[] args) throws IOException {

        System.out.println("------");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);

        context.start();
        ComsumerComponet service = context.getBean(ComsumerComponet.class);

        while (true){
            System.in.read();
            String hello = service.sayHello("world");

            System.out.println("result:"+hello);
        }

    }


    @Configuration
    @PropertySource("classpath:/dubbo-consumer.properties")
    @ComponentScan(basePackages =  "com.lagou.bean" )
    @EnableDubbo
    static class ConsumerConfiguration{

    }

}
