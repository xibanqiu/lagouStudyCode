package com.lagou.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class Set_Data_Sample {

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

        String path = "/lg_curator/c1";

        Stat stat = new Stat();

        byte[] bytes = client2.getData().storingStatIn(stat).forPath(path);
        System.out.println(new String(bytes));

        System.out.println("success create znode"+path);

        // 更新节点数据
        int version = client2.setData().withVersion(stat.getVersion()).forPath(path).getVersion();

        System.out.println("Success set node for : " + path + ", new version: "+version);

        // 这里会报错，出现一个不存在的version
        client2.setData().withVersion(stat.getVersion()).forPath(path).getVersion();

    }


}
