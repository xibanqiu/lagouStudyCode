package com.lagou.semaphonedemo;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author xibanqiu
 * created by sheting on 2021/4/20
 */
public class MyThread extends Thread{

    private final Semaphore semaphore;
    private final Random random = new Random();

    public MyThread(Semaphore semaphore,String name) {
        super(name);
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println( Thread.currentThread().getName() + "- 抢座位成功" );

            Thread.sleep(random.nextInt(1000));

            System.out.println( Thread.currentThread().getName() + "- 作业写完了，腾出座位" );


        }catch (Exception e){
            e.printStackTrace();
        }
        semaphore.release();
    }


    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(2);

        for (int i = 0; i < 5 ; i++) {
            new MyThread(semaphore ,"学生 - " + (i+1)).start();
        }

    }


}
