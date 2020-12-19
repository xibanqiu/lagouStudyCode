package com.lagou.controller;

import com.lagou.annotaiton.MyAutowired;
import com.lagou.annotaiton.MyController;
import com.lagou.annotaiton.MyRequestMapping;
import com.lagou.service.DemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MyController
@MyRequestMapping("/")
public class DemoController {

    @MyAutowired
    private DemoService demoService;

    public String query(HttpServletRequest request, HttpServletResponse response, String name){
        return demoService.get(name);
    }

}
