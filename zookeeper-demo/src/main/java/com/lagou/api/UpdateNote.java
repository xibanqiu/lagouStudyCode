package com.lagou.api;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


public class UpdateNote implements Watcher {

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
         /*
         客户端可以通过创建⼀个zk实例来连接zk服务器
         new Zookeeper(connectString,sesssionTimeOut,Wather)
         connectString: 连接地址：IP：端⼝
         sesssionTimeOut：会话超时时间：单位毫秒
         Wather：监听器(当特定事件触发监听时，zk会通过watcher通知到客户端)
         */
        zooKeeper = new ZooKeeper("127.0.0.1:2181",50000,new UpdateNote());

        System.out.println(zooKeeper.getState());

        Thread.sleep(Integer.MAX_VALUE);  // 线程永远睡眠

    }

    // 当前类实现了Watcher接⼝，重写了process⽅法，该⽅法负责处理来⾃Zookeeper服务端的
    @Override
    public void process(WatchedEvent watchedEvent) {

        // 修改数据
        try {
            updateNodeSync();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void updateNodeSync()throws Exception{

        /*
         path:路径
         data:要修改的内容 byte[]
         version:为-1，表示对最新版本的数据进⾏修改
         zooKeeper.setData(path, data,version);
         */

        byte[] data = zooKeeper.getData("/lg_persistent", false, null);
        System.out.println("修改前的值:"+new String(data));

        //修改 stat:状态信息对象 -1:最新版本
        Stat stat = zooKeeper.setData("/lg_persistent", "客户端修改内容".getBytes(), -1);

        byte[] data2 = zooKeeper.getData("/lg_persistent", false, null);

        System.out.println("修改后的值:"+new String(data2));
    }



}
