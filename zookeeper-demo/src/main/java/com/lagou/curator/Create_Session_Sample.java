package com.lagou.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Create_Session_Sample {

    public static void main(String[] args) {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);  //重试策略

        // 第一种 创建的方式
        CuratorFramework client1 = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 5000, 300, retryPolicy);

        client1.start();

        System.out.println("Zookeeper session1 established. ");

        CuratorFramework client2 = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181") //server地址
                .connectionTimeoutMs(5000)       // 会话超时时间
                .sessionTimeoutMs(3000)         // 连接超时时间
                .retryPolicy(retryPolicy)      //重试策略
                .namespace("base")
                .build();

        client2.start();
        System.out.println("Zookeeper session2 established. ");
    }


}
