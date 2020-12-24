package com.lagou.demo;

import com.lagou.demo.controller.HelloController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class Springboot1QuilystartApplicationTests {

    @Autowired
    HelloController helloController;

    @Test
    void contextLoads() {

        System.out.println(helloController.test1());
    }

}
