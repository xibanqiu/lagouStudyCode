package com.lagou.servlet;

import com.lagou.pojo.Request;
import com.lagou.pojo.Response;
import com.lagou.utils.HttpProcotolUtil;

import java.io.IOException;

public class LagouServlet extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) {

        String content = "<h1>GET </h1>";

        try {
            response.outputContent(HttpProcotolUtil.getHttpHead200(content.length())+content);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void doPost(Request request, Response response) {

        String content = "<h1>POST </h1>";

        try {
            response.outputContent(HttpProcotolUtil.getHttpHead200(content.length())+content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void destory() {

    }
}
