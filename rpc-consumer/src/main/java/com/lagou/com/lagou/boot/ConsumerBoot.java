package com.lagou.com.lagou.boot;

import com.lagou.client.RPCConsumer;
import com.lagou.server.UserService;

public class ConsumerBoot {

    //参数定义 的规则
    private static final String PROVIDER_NAME = "UserService#sayHello#";

    public static void main(String[] args) throws InterruptedException {
        //1.创建代理对象
        UserService service = (UserService)RPCConsumer.createProxy(UserService.class, PROVIDER_NAME);

        //2.循环给服务器写数据
        while (true){
            String result = service.sayHello("are you ok !!");
            System.out.println(result);
            Thread.sleep(2000);
        }

    }

}
