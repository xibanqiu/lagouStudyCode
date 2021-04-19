package com.lagou.demo2;

/**
 * @author xibanqiu
 * created by sheting on 2021/4/19
 */
public class Main {


    public static void main(String[] args) {

//        MyQueue myQueue = new MyQueue();
//        ConsumerThread consumerThread = new ConsumerThread(myQueue);
//        ProducerThread producerThread = new ProducerThread(myQueue);
//
//        producerThread.start();
//        consumerThread.start();

        MyQueue myQueue = new MyQueue();
        for (int i = 0; i < 3; i++) {
            new ConsumerThread(myQueue).start();
        }
        for (int i = 0; i < 5; i++) {
            new ProducerThread(myQueue).start();
        }
    }

}
