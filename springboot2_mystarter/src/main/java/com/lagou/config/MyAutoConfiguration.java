package com.lagou.config;

import com.lagou.pojo.SimpleBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass
public class MyAutoConfiguration {


    static {
        System.out.println("MyAutoConfiguration init...");
    }

    @Bean
    public SimpleBean simpleBean(){

        return new SimpleBean();
    }


}
