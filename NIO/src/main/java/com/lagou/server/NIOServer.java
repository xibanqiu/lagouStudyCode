package com.lagou.server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.security.Key;
import java.util.Iterator;
import java.util.Scanner;

public class NIOServer extends Thread{

    //1. 定义多路复用器
    private Selector selector;

    //2. 定义读写缓冲区
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    // 3. 定义构造方法 --> 初始化 端口
    public NIOServer(int port) {
        // 类构造前的初始化
        init(port);
    }

    // 4、定义初始化方法
    private void init(int port) {

        try {
            System.out.println("服务器正在启动");

            // 1) 开启多路复用器
            this.selector = Selector.open();

            // 2) 开启服务通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            // 3) 设置为非阻塞
            serverSocketChannel.configureBlocking(false);

            // 4) 绑定端口
            serverSocketChannel.bind(new InetSocketAddress(port));

            // 5) 注册，标记服务通标状态
            serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);

            System.out.println("服务器启动完毕!!");

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    // 5 定义 Thread 的 run 方法
    public void run(){
        while (true){

            try {
                //1) 当有至少一个 通道被选中，执行此方法
                this.selector.select();

                //2) 获取选中的通道编号集合
                Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();

                //3) 遍历keys
                while(keys.hasNext()){

                    SelectionKey key = keys.next();

                    //4) 当前key 需要从keys集合中 移出，如果不移出，下次还会循环会执行对应的逻辑，造成业务混乱
                    keys.remove();

                    //5) 判断通道是否有效
                    if(key.isValid()){

                        //6) 判断是否可用
                        try {
                            if(key.isAcceptable()){
                                accept(key);
                            }

                        }catch (Exception e){
                            key.cancel();
                        }


                        //6) 判断是否可读
                        try {
                            if(key.isAcceptable()){
                                accept(key);
                            }

                        }catch (Exception e){
                            key.cancel();
                        }


                        //7) 判断是否可读
                        try {
                            if(key.isReadable()){
                                read(key);
                            }

                        }catch (Exception e){
                            key.cancel();
                        }


                        //8) 判断是否可写
                        try {
                            if(key.isWritable()){
                                write(key);
                            }

                        }catch (Exception e){
                            key.cancel();
                        }

                    }

                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }



    private void accept(SelectionKey key) {

        try {
            // 1、当前通道 init方法中注册代理selector 中 的ServerSocketChannel
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();

            // 2、 阻塞方法，客户端发起后请求 返回
            SocketChannel channel = serverSocketChannel.accept();

            //3、serverSocketChannel 设置为非阻塞
            channel.configureBlocking(false);

            // 4、设置对应客户端的通道标记，设置次通道为可读时使用
            channel.register(this.selector,SelectionKey.OP_READ);

        }catch (Exception e){

            e.printStackTrace();
        }

    }


    // 使用通道 读取数据
    private void read(SelectionKey key) {

        try {
            // 清空缓存
            this.readBuffer.clear();

            // 获取当前通道对象
            SocketChannel channel = (SocketChannel) key.channel();

            // 将通道的数据(客户发送的data)读到缓存中
            int readLen = channel.read(readBuffer);

            //如果通道中没有数据，退出
            if(readLen == -1){
                //关闭通道
                key.channel().close();

                //关闭连接
                key.cancel();
                return;
            }

            // Buffer 中有游标，游标不会重置，需要我们调用flip重置，否则读取不一致
            this.readBuffer.flip();

            // 创建有效字节长度数组
            byte[] bytes = new byte[readBuffer.remaining()];

            // 读取buffer中 数据保存在字节数组
            readBuffer.get(bytes);
            System.out.println("收到了从客户端 "+ channel.getRemoteAddress() + " : "+ new String(bytes,"UTF-8"));

            // 注册通道，标记为写操作
            channel.register(this.selector,SelectionKey.OP_WRITE);



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void write(SelectionKey key) {
        try {

            // 清空缓存
            this.writeBuffer.clear();

            // 获取当前通道对象
            SocketChannel channel = (SocketChannel) key.channel();

            // 录入数据
            Scanner scanner = new Scanner(System.in);

            try {
                System.out.println("即将发送数据到客户端..");

                String line = scanner.nextLine();

                // 把录入数据写到 Buffer中
                writeBuffer.put(line.getBytes("UTF-8"));

                // 重置缓存游标
                writeBuffer.flip();

                channel.write(writeBuffer);
                channel.register(this.selector,SelectionKey.OP_READ);

            }catch (Exception e){
                e.printStackTrace();
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 6 main方法启动线程
    public static void main(String[] args) {
        new Thread(new NIOServer(8888)).start();
    }

}
