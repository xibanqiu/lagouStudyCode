package com.lagou.service.impl;

import com.lagou.annotaiton.MyService;
        import com.lagou.service.DemoService;

@MyService
public class DemoServiceImpl implements DemoService {
    @Override
    public String get(String name) {

        System.out.println(name);

        return "serviceimpl --- " +name ;
    }
}
