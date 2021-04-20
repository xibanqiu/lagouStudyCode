package com.lagou.demo3intrerrupted;

/**
 * @author xibanqiu
 * created by sheting on 2021/4/19
 */
public class MyJoin extends Thread {


    @Override
    public void run() {

        int size = 100;
        for (int i = 0; i < size; i++) {
            System.out.println(Thread.currentThread().getName() + i);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        MyJoin myJoin = new MyJoin();
        myJoin.setName("线程一");
        MyJoin myJoin2 = new MyJoin();
        myJoin2.setName("线程二");

        myJoin.start();
        myJoin.join();
        myJoin2.start();
        myJoin2.join();

        System.out.println("main线程 - 执行完成");
    }
}
