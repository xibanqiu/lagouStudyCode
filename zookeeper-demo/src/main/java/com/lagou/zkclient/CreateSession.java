package com.lagou.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class CreateSession {

    /*
    创建一个 zkClient 实例来进行连接
    注意:zkClient 通过对zookeeperAPI 内部包装，将这个异步的会话创建过程中同步化了
     */

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        System.out.println("Zookeeper session established!! ");

    }


}
