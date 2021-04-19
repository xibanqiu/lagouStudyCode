package com.lagou.demo2;

import java.util.Random;

/**
 * @author xibanqiu
 * created by sheting on 2021/4/19
 */
public class ProducerThread extends Thread {

    private final MyQueue myQueue;

    private final Random random = new Random();

    private int index = 0;
    public ProducerThread(MyQueue myQueue) {
        this.myQueue = myQueue;
    }

    @Override
    public void run() {
        while (true){

            String tmp = "ele-" + index;
            System.out.println( "生产了 - " + tmp );
            myQueue.put(tmp);
            index++;

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
