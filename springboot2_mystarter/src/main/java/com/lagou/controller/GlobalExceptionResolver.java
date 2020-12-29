package com.lagou.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

//@ControllerAdvice
//public class GlobalExceptionResolver {
//
//    @ExceptionHandler(ArithmeticException.class)
//    public ModelAndView handlerException(ArithmeticException e, HttpServletResponse httpServletResponse){
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("msg",e.getMessage());
//
//        modelAndView.setViewName("error");
//
//        return modelAndView;
//
//    }
//
//}
