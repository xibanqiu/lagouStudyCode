package com.lagou.server;


import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * 服务端的程序
 */
public class Server {

    public static void main(String[] args) {
        try {

            // 创建一个远程对象，同时也胡创建一个stud对象，skeleton 对象
            Hello hello = new HelloImpl();

            // 本地主机上的远程对象注册表registry的实例，并指定端口为 8888 ，这一步 必不可少(java 默认端口是1099)
            // 必不可缺的一步，缺少注册表创建，则无法 绑定对象到远程 注册表上
            LocateRegistry.createRegistry(8080);  // 启动注册服务

            try {
                Naming.bind("//127.0.0.1:8080/zm",hello);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("service bind already!!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
