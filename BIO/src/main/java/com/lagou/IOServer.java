package com.lagou;


import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket();

        serverSocket.bind(new InetSocketAddress("127.0.0.1",8081));

        while (true){

            Socket socket = serverSocket.accept();

            new Thread(()->{
                try {
                    byte[] bytes = new byte[1024];
                    InputStream inputStream = socket.getInputStream();
                    int length = inputStream.read(bytes);

                    System.out.println(new String(bytes,0,length));

                    socket.getOutputStream().write(bytes,0,length);

                    socket.getOutputStream().flush();


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }).start();

        }

    }


}
