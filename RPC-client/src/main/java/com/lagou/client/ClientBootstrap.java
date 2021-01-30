package com.lagou.client;

import com.lagou.consumer.RpcConsumer;
import com.lagou.service.UserService;

public class ClientBootstrap {



    public static final String providerName = "UserService#sayHello#";

    public static void main(String[] args) throws InterruptedException {
        RpcConsumer rpcConsumer = new RpcConsumer();
        UserService userService = (UserService)rpcConsumer.createProxy(UserService.class, providerName);

        while (true){
            System.out.println(userService.sayHello("are you ok ?"));
            Thread.sleep(2000);
        }

    }


}
