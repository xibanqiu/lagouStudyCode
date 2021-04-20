package com.lagou.demo3intrerrupted;

/**
 * @author xibanqiu
 * created by sheting on 2021/4/19
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();
        Thread.sleep(100);

        myThread.interrupt();
        Thread.sleep(100);
        System.exit(0);
    }


}
