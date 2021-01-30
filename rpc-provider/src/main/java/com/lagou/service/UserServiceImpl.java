package com.lagou.service;


import com.lagou.handler.UserServiceHandler;
import com.lagou.server.UserService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class UserServiceImpl implements UserService {

    // 将来用户 要远程调用的方法
    @Override
    public String sayHello(String msg) {

        String word = "客户端 发的数据--->" + msg;
        System.out.println(word);
        return "服务端返回的数据："+msg;
    }

    // 创建一个方法启动服务器
    public static  void startServer(String ip , int port) throws InterruptedException {

        // 1、 创建两个线程池对象
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        // 2、 创建服务器的启动引导对象
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 3、 配置启动引导对象
        serverBootstrap.group(bossGroup,workGroup)
                // 设置通道为Nio
                .channel(NioServerSocketChannel.class)
                // 创建监听
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // 获取管道对象
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();

                        // 设置编码
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());

                        // 把我们自定义 一个ChannelHandler 添加到通道中
                        pipeline.addLast(new UserServiceHandler());
                    }
                });


        // 绑定  服务器和端口
        serverBootstrap.bind(ip,port).sync();

    }

}
