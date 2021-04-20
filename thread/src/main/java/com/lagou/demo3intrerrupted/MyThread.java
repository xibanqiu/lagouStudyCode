package com.lagou.demo3intrerrupted;

/**
 * @author xibanqiu
 * created by sheting on 2021/4/19
 */
public class MyThread extends Thread {

    @Override
    public void run() {

        while (true){
            boolean interrupted = isInterrupted();
            System.out.println("中断标记 " + interrupted);

        }

    }
}
