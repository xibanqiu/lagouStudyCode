package com.lagou.demo1;

/**
 * @author xibanqiu
 * created by sheting on 2021/4/18
 */
public class MyTest {


    public static void main(String[] args) {

        MyThread thread = new MyThread();
        MyThread thread1 = new MyThread();

        thread.setName("线程1");
        thread1.setName("线程2");

        thread.setPriority(1);
        thread1.setPriority(10);

        thread1.setDaemon(true);
        thread.setDaemon(true);

        thread.start();
        thread1.start();

    }

}
