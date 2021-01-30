package com.lagou.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;

//客户端给服务器发送数据
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //1.创建连接池对象
        NioEventLoopGroup group = new NioEventLoopGroup();

        //2.创建客户端的启动引导类 BootStrap
        Bootstrap bootstrap = new Bootstrap();

        //3.配置启动引导类
        bootstrap.group(group)
                //设置通道为Nio
                .channel(NioSocketChannel.class)
                //设置Channel初始化监听
                .handler(new ChannelInitializer<Channel>() {
                    //当前该方法监听channel是否初始化
                    protected void initChannel(Channel channel) throws Exception {
                        //设置编码
                        channel.pipeline().addLast(new StringEncoder());
                    }
                });

        //4.使用启动引导类连接服务器 , 获取一个channel
        Channel channel = bootstrap.connect("127.0.0.1", 9999).channel();

        //5.循环写数据给服务器
        while (true) {
            //给服务器写数据
            channel.writeAndFlush("hello server .. this is client ...");
            Thread.sleep(2000);
        }
    }
}
