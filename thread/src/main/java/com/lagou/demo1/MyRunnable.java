package com.lagou.demo1;

/**
 * @author xibanqiu
 * created by sheting on 2021/4/18
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {

        while (true){
            System.out.println(Thread.currentThread().getName() + " run " );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();
    }

}
