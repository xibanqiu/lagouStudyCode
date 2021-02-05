package com.lagou.api;

import org.apache.zookeeper.*;

import java.util.List;

public class GetNoteData implements Watcher {

    private static ZooKeeper zooKeeper;


    public static void main(String[] args) throws Exception {

         /*
         客户端可以通过创建⼀个zk实例来连接zk服务器
         new Zookeeper(connectString,sesssionTimeOut,Wather)
         connectString: 连接地址：IP：端⼝
         sesssionTimeOut：会话超时时间：单位毫秒
         Wather：监听器(当特定事件触发监听时，zk会通过watcher通知到客户端)
         */

        zooKeeper = new ZooKeeper("127.0.0.1:2181",50000,new GetNoteData());

        System.out.println(zooKeeper.getState());

        Thread.sleep(Integer.MAX_VALUE);  // 线程永远睡眠


    }

    // 当前类实现了Watcher接⼝，重写了process⽅法，该⽅法负责处理来⾃Zookeeper服务端的
    @Override
    public void process(WatchedEvent watchedEvent) {
        // 当连接创建了,服务端 发送给 客户端syncConnected 事件
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
            // 调用获取单个节点数据方法
            try {
                getNoteData();
                getChildrenNotes();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // 子节点 列表发生变化时，服务器 会发出 NodeChildrenChanged 通知，但不会把变化情况告诉 客户端
        // 需要 客户端自行获取,且通知是一次性的，需反复注册监听
        if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged){
            List<String> children = null;
            try {
                children = zooKeeper.getChildren(watchedEvent.getPath(), true);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(children);

        }

    }

    private static void getNoteData()throws Exception{

        /**
         * path : 获取数据的路径
         * watch : 是否开启监听
         * stat : 节点状态信息
         * null: 表示获取最新版本的数据
         * zk.getData(path, watch, stat);
         */

        byte[] data = zooKeeper.getData("/lg_persistent/lg-children", true, null);
        System.out.println(new String(data,"utf-8"));

    }

    private static void getChildrenNotes()throws Exception{

        /*
         path:路径
         watch:是否要启动监听，当⼦节点列表发⽣变化，会触发监听
         zooKeeper.getChildren(path, watch);
         */
        List<String> childrens = zooKeeper.getChildren("/lg_persistent", true);

        System.out.println(childrens);

    }


}
