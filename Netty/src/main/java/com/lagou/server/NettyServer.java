package com.lagou.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

        // 1、创建 NioEventLoopCroup的两个实例：bossGroup workGroup
        // 当前这两个实例代表两个线程，默认线程数为CPU 核心数乘2
        // bossGroup 接受客户端 传过来的请求
        // workGroup 处理请求


        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();


        // 2、创建 服务启动辅助类:组装一些必要的组件
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 设置组，第一个bossGroup 负责连接，workGroup 负责连接之后的io处理
        serverBootstrap.group(bossGroup,workGroup)
                // channel 方法指定服务器监听的通道类型
                .channel(NioServerSocketChannel.class)
                // 设置channel handler ,每一个客户端 连接后，给定一个监听器进行处理
                .childHandler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {

                        // 传输通道
                        ChannelPipeline pipeline = nioServerSocketChannel.pipeline();

                        // 在通道 上添加对通道的处理器，该处理器可能还是要给监听器
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());

                        pipeline.addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

                                System.out.println(s);
                            }
                        });

                    }
                });

        //bind 监听端口
        ChannelFuture channelFuture = serverBootstrap.bind(8000).sync();

        System.out.println(" tcp server start suceess");

        // 会阻塞等待 知道服务器的channel 关闭
        channelFuture.channel().closeFuture().sync();

    }

}
