package com.lagou.server;

import com.lagou.Utils.HttpProtocolUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * My tomcat 1.0
 */

public class Bootstrap {

    // 配置的端口号
    private int port = 8080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    /**
     *  MyTomcat 启动需要初始化展开的一些操作
     */
    public void start() throws IOException {

        /**
         * 完成MyTomcat 1.0 版本
         * 需求：浏览器请求 http:// localhost:8080 ,返回一个固定的字符串到页面 "Hello MyTomcat!!"
         */
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("====>>> My Tomcat" + port);

        while (true){
            Socket socket = serverSocket.accept();

            // 有了socket，接收到请求，获取输入流
            OutputStream outputStream = socket.getOutputStream();
            String data = "Hello MyTomcat!!";
            outputStream.write((HttpProtocolUtil.getHttpHead200(data.length()) + data).getBytes()) ;

            socket.close();
        }

    }


    /**
     * My Tomcat 的程序入口
     */
    public static void main(String[] args) {

        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
