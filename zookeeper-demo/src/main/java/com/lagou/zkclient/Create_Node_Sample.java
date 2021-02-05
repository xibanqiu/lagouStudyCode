package com.lagou.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class Create_Node_Sample {


    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");

        System.out.println("ZooKeeper session established.");

        zkClient.createPersistent("/lg-zkClient/lg-c1",true);
        System.out.println("success create znode.");
    }


}
