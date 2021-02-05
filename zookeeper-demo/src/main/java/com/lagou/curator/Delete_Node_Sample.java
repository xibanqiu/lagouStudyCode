package com.lagou.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class Delete_Node_Sample {

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


        //删除节点
        String path = "/lg_curator";
        client2.delete().deletingChildrenIfNeeded().withVersion(-1).forPath(path);

        System.out.println("success create znode"+path);

    }


}
