package com.lagou.boot;

import com.lagou.service.UserServiceImpl;

public class ServiceBoot {

    public static void main(String[] args) throws InterruptedException {

        UserServiceImpl.startServer("127.0.0.1",8999);
    }

}
