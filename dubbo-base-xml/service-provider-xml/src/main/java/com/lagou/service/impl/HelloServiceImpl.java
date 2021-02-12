package com.lagou.service.impl;

import com.lagou.service.HelloService;

public class HelloServiceImpl implements HelloService {


    public String sayHello(String name) {
        return "hello:"+name;
    }


}
