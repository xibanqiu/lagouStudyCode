package com.lagou.hashdemo;



// 普通的hash 算法
public class Demo1 {


    public static void main(String[] args) {

        String[] clients = {"127.0.0.0","127.0.0.1","127.0.0.2"};

        int servers = 5 ; //定义 服务器 的数量

        for (String client : clients) {

            int abs = Math.abs(client.hashCode());

            int index = abs % servers;

            System.out.println( "客户端： "+client+" 被路由到 服务器编号 "+index);

        }

    }

}
