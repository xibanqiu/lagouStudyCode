package com.lagou.server;


import com.lagou.pojo.Request;
import com.lagou.pojo.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Bootstrap {

    private Integer port = 8081;

    /**
     * 2.0 tomcat 的版本
     * @throws IOException
     */
    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);

        while (true){
            Socket socket = serverSocket.accept();
            // 通过输入流 获取请求信息
            InputStream inputStream = socket.getInputStream();

            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            response.outputHtml(request.getUrl());

            socket.close();
        }

    }


    public static void main(String[] args) {

        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
