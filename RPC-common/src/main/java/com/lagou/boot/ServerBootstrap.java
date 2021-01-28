package com.lagou.boot;

import com.lagou.service.UserServiceImpl;

public class ServerBootstrap {

    public static void main(String[] args) {
        UserServiceImpl.startServer("localhost",8990);
    }

}
