package com.lagou.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class Create_Node_Sample {

    public static void main(String[] args) throws Exception {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        CuratorFramework client2 = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .connectionTimeoutMs(5000)
                .sessionTimeoutMs(3000)
                .retryPolicy(retryPolicy)
                .build();

        client2.start();
        System.out.println("Zookeeper session2 established. ");


        //添加节点
        String path = "/lg_curator/c1";
        client2.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path,"init".getBytes());

        Thread.sleep(1000);
        System.out.println("success create znode"+path);

    }


}
