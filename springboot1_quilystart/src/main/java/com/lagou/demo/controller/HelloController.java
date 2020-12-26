package com.lagou.demo.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @RequestMapping("/hello")
    public String test1(){


        return "京津冀!!";

    }


}
