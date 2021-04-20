package com.lagou.demo1;

/**
 * @author xibanqiu
 * created by sheting on 2021/4/18
 */
public class MyThread extends Thread {

    @Override
    public void run() {

        while (true){
            System.out.println(Thread.currentThread().getName() + "运行了" );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

        MyThread thread = new MyThread();
        MyThread thread1 = new MyThread();

        thread.start();
        thread1.start();
    }

}
