package com.lagou.server;


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

            int count = 0;
            while (count ==  0){
                int available = inputStream.available();
            }
            byte[] bytes = new byte[count];
            inputStream.read(bytes);

            System.out.println("=====>>>请求信息"+ new String(bytes));

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
