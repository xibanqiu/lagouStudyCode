package com.lagou;


import java.io.IOException;
import java.net.Socket;

public class IOClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 8081);

        socket.getOutputStream().write("hello".getBytes());

        socket.getOutputStream().flush();


        System.out.println("server send data ===");

        byte[] bytes = new byte[1024];

        int length = socket.getInputStream().read(bytes);

        System.out.println(new String(bytes,0,length));

        socket.close();


    }

}
