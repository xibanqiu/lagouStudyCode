package com.lagou.servlet;

import com.lagou.pojo.Request;
import com.lagou.pojo.Response;

public interface Servlet {

    public void init();
    public void destory();

    public void service(Request request, Response response);


}
