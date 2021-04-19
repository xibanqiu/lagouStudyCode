package com.lagou.demo1;

import java.util.concurrent.*;

/**
 * @author xibanqiu
 * created by sheting on 2021/4/19
 */
public class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {

        Thread.sleep(5000);
        return "hello world call() invoked!!";
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        MyCallable myCallable = new MyCallable();

//        FutureTask<String> futureTask = new FutureTask<String>(myCallable) ;
//        new Thread(futureTask).start();
//        String result = futureTask.get();
//        System.out.println(result);


        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,5,1,TimeUnit.SECONDS,new ArrayBlockingQueue<>(10)){

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
            }
        };

//        Future<String> future = executor.submit(myCallable);
        Future<?> future = executor.submit(new Runnable() {
            @Override
            public void run() {

                System.out.println(1111);
            }
        });
        String s = (String) future.get();

        System.out.println(s);
        executor.shutdown();
    }


}
