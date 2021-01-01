package com.lagou.servlet;

import com.lagou.pojo.Request;
import com.lagou.pojo.Response;
import com.lagou.utils.HttpProtocolUtil;

public class LagouServlet extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) throws Exception {

        String content ="<h1> LagouServlet get</h1>" ;
        try {
            response.output(HttpProtocolUtil.getHttpHead200(content.length())+content);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
        String content ="<h1> LagouServlet post</h1>" ;
        try {
            response.output(HttpProtocolUtil.getHttpHead200(content.length())+content);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {
        System.out.println("init方法");
    }

    @Override
    public void destory() throws Exception {
        System.out.println("destory方法");
    }
}
