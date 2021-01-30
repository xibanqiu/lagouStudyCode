package com.lagou.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {


        // 客户端的启动辅助类
        Bootstrap bootstrap = new Bootstrap();
        // 线程池的实例
        NioEventLoopGroup group = new NioEventLoopGroup();

        // 添加到组中
        bootstrap.group(group)
                // channel 方法指定通道类型
                .channel(NioSocketChannel.class)
                // 通道初始化了
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {

                        channel.pipeline().addLast(new StringEncoder());

                    }
                });

        Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();

        while (true) {
            channel.writeAndFlush(new Date() + ": hello world!");
            Thread.sleep(2000);
        }

    }

}
