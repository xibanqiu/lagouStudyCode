package com.lagou.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class Get_Data_Sample {

    public static void main(String[] args) throws InterruptedException {
        String path = "/lg-zkClient-Ep";
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000);

        boolean exists = zkClient.exists(path);

        if(!exists){

            zkClient.createEphemeral(path,"555");

        }

        //注册监听
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            public void handleDataChange(String path, Object data) throws
                    Exception {
                System.out.println(path+"该节点内容被更新，更新后的内容"+data);
            }

            public void handleDataDeleted(String s) throws Exception {
                System.out.println(s+" 该节点被删除");
            }
        });

        // 获取节点内容
        Object o = zkClient.readData(path);
        System.out.println(o);


        zkClient.writeData(path,"666");
        Thread.sleep(1000);

        // 删除节点
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);

    }

}
