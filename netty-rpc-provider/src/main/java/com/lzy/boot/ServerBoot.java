package com.lzy.boot;

import com.lzy.service.UserServiceImpl;

public class ServerBoot {

    public static void main(String[] args) throws InterruptedException {
        UserServiceImpl.startServer("127.0.0.1", 8999);
    }
}
