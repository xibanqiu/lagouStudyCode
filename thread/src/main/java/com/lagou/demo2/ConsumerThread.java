package com.lagou.demo2;

import java.util.Random;

/**
 * @author xibanqiu
 * created by sheting on 2021/4/19
 */
public class ConsumerThread extends Thread {


    private final MyQueue myQueue;

    private Random random = new Random();

    public ConsumerThread(MyQueue myQueue) {
        this.myQueue = myQueue;
    }

    @Override
    public void run() {

        while (true){

            String s = myQueue.get();
            System.out.println("\t\t 消费了" + s );

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
