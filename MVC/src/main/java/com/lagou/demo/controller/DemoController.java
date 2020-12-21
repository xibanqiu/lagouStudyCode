package com.lagou.demo.controller;



import com.lagou.demo.service.DemoService;
import com.lagou.mvcframework.annotaiton.MyAutowired;
import com.lagou.mvcframework.annotaiton.MyController;
import com.lagou.mvcframework.annotaiton.MyRequestMapping;
import com.lagou.mvcframework.annotaiton.MySecurity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MyController
@MySecurity({"tom","jerry"})
@MyRequestMapping("/demo")
public class DemoController {

    @MyAutowired
    private DemoService demoService;

    @MyRequestMapping("/query")
    @MySecurity({"tom"})
    public String query(HttpServletRequest request, HttpServletResponse response, String name){
        return demoService.get(name);
    }

}
