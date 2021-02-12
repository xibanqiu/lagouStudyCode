package com.lagou.service.impl;

import com.lagou.service.HelloService;

public class HelloServiceMock implements HelloService {


    @Override
    public String sayHello(String name) {
        return "hello mock";
    }
}
