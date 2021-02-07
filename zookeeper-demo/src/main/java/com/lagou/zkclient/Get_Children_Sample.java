package com.lagou.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

public class Get_Children_Sample {


    public static void main(String[] args) throws Exception {
        String path = "/lg-zkClient";
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);


        // 注册监听事件
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {

                System.out.println(parentPath + " 's child changed,currentChilds:" + currentChilds);

            }
        });

        zkClient.createPersistent("/lg-zkClient");
        Thread.sleep(1000);
        zkClient.createPersistent("/lg-zkClient/c1");
        Thread.sleep(1000);
        zkClient.delete("/lg-zkClient/c1");
        Thread.sleep(1000);
        zkClient.delete(path);

        System.out.println("执行结束");
        Thread.sleep(Integer.MAX_VALUE);

    }

}
