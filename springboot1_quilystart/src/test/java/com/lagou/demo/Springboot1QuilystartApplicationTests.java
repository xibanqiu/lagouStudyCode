package com.lagou.demo;

import com.lagou.demo.controller.HelloController;
import com.lagou.demo.pojo.MyProperties;
import com.lagou.demo.pojo.Person;
import com.lagou.demo.pojo.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class Springboot1QuilystartApplicationTests {

    @Autowired
    HelloController helloController;

    @Autowired
    private Person person;

    @Autowired
    private Student student;

    @Autowired
    private MyProperties myProperties;

    @Test
    public void contextLoads() {

        System.out.println(helloController.test1());
    }


    @Test
    public void configurationTest(){

        System.out.println(person);
    }

    @Test
    public void configurationTest2(){

        System.out.println(student);

    }

    @Test
    public void configurationTest3(){

        System.out.println(myProperties);

    }


    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void iocTest(){

        System.out.println(applicationContext.containsBean("myService"));

    }

}
