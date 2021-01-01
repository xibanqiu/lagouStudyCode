package com.lagou.servlet;

import com.lagou.pojo.Request;
import com.lagou.pojo.Response;

public interface Servlet {

    void init() throws Exception;

    void destory() throws Exception;

    void service(Request request, Response response) throws Exception;

}
