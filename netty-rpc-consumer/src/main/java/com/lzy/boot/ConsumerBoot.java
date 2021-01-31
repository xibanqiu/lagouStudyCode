package com.lzy.boot;

import com.lzy.client.RpcConsumer;
import com.lzy.service.IUserService;

public class ConsumerBoot {

//    private static final String PROVIDER_NAME = "UserService#sayHello#";
    public static void main(String[] args) throws InterruptedException {
        IUserService iUserService = (IUserService) RpcConsumer.createProxy(IUserService.class);
        while (true) {
            iUserService.sayHello("are you ok?;");
            System.out.println("客户端已响应");
            Thread.sleep(2000);
        }
    }
}
