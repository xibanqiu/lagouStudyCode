package com.lagou.api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import static org.apache.zookeeper.ZooDefs.OpCode.exists;


public class DeleteNote implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
         /*
         客户端可以通过创建⼀个zk实例来连接zk服务器
         new Zookeeper(connectString,sesssionTimeOut,Wather)
         connectString: 连接地址：IP：端⼝
         sesssionTimeOut：会话超时时间：单位毫秒
         Wather：监听器(当特定事件触发监听时，zk会通过watcher通知到客户端)
         */
        zooKeeper = new ZooKeeper("127.0.0.1:2181",50000,new DeleteNote());

        System.out.println(zooKeeper.getState());

        Thread.sleep(Integer.MAX_VALUE);  // 线程永远睡眠

    }

    // 当前类实现了Watcher接⼝，重写了process⽅法，该⽅法负责处理来⾃Zookeeper服务端的
    @Override
    public void process(WatchedEvent watchedEvent) {

        // 删除数据
        try {
            deleteNodeSync();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void deleteNodeSync()throws Exception{

        /*
         zooKeeper.exists(path,watch) :判断节点是否存在
         zookeeper.delete(path,version) : 删除节点
         */

        Stat exists = zooKeeper.exists("/lg_persistent/lg-children", false);
        System.out.println(exists == null ? "该节点不存在":"该节点存在");

        //修改 stat:状态信息对象 -1:最新版本
        zooKeeper.delete("/lg_persistent/lg-children",-1);

        Stat exists2 = zooKeeper.exists("/lg_persistent/lg-children", false);

        System.out.println(exists2 == null ? "该节点不存在":"该节点存在");
    }



}
