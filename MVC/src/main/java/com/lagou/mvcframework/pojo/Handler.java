package com.lagou.mvcframework.pojo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Handler {

    private Object controller; // 方法反射时需要 用到 类的class
    private Method method;

    private Pattern pattern; // spring url 支持正则
    private Map<String,Integer> paramIndexMapping;   //参数顺序 ，是为了 进行参数绑定，key 是参数名，value 代表是第几个参数 <name>

    private String[] MySecurityClass = {};

    private String[] MySecurityMethod = {};


    public Handler(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
        this.paramIndexMapping = new HashMap<>();
    }


    public String[] getMySecurityClass() {
        return MySecurityClass;
    }

    public void setMySecurityClass(String[] mySecurityClass) {
        MySecurityClass = mySecurityClass;
    }

    public String[] getMySecurityMethod() {
        return MySecurityMethod;
    }

    public void setMySecurityMethod(String[] mySecurityMethod) {
        MySecurityMethod = mySecurityMethod;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Map<String, Integer> getParamIndexMapping() {
        return paramIndexMapping;
    }

    public void setParamIndexMapping(Map<String, Integer> paramIndexMapping) {
        this.paramIndexMapping = paramIndexMapping;
    }
}
