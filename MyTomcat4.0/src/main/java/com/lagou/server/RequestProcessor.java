package com.lagou.server;

import com.lagou.pojo.Request;
import com.lagou.pojo.Response;
import com.lagou.servlet.HttpServlet;

import java.net.Socket;
import java.util.Map;

public class RequestProcessor extends Thread {

    private Socket socket;

    private Map<String, HttpServlet> servletMap ;

    public RequestProcessor(Socket socket, Map<String, HttpServlet> servletMap){
        this.socket = socket;
        this.servletMap = servletMap;
    }

    @Override
    public void run() {

        try{

            Request request = new Request(socket.getInputStream());

            Response response = new Response(socket.getOutputStream());

            // 静态资源
            if(servletMap.get(request.getUrl()) == null){
                Thread.sleep(100000);
                response.outputHtml(request.getUrl());
            }else {
                HttpServlet httpServlet = servletMap.get(request.getUrl());
                httpServlet.service(request,response);
            }

            socket.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
