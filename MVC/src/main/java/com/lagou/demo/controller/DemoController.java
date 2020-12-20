package com.lagou.demo.controller;



import com.lagou.demo.service.DemoService;
import com.lagou.mvcframework.annotaiton.MyAutowired;
import com.lagou.mvcframework.annotaiton.MyController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MyController
public class DemoController {

    @MyAutowired
    private DemoService demoService;

    public String query(HttpServletRequest request, HttpServletResponse response, String name){
        return demoService.get(name);
    }

}
