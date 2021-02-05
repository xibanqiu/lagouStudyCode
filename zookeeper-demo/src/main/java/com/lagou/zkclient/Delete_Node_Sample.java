package com.lagou.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class Delete_Node_Sample {


    public static void main(String[] args) {
        String path = "/lg-zkClient/lg-c1";
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);
        zkClient.deleteRecursive(path);
        System.out.println("success delete znode.");
    }


}
