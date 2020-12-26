package com.lagou.springboot2_mystatertest;

import com.lagou.pojo.SimpleBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot2MystaterTestApplicationTests {

    @Autowired
    private SimpleBean simpleBean;

    @Test public void zdyStarterTest(){
        System.out.println(simpleBean);
    }

}
