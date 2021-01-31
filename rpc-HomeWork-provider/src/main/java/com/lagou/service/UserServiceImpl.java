package com.lagou.service;

import com.lagou.handler.UserServiceHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Override
    public String sayHello(String msg) {

        System.out.println("客户端发送的数据 ---> " + msg);
        return "服务端返回的数据 :" + msg;
    }


    public static void startServer(String ip , int port) throws InterruptedException {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

                            ChannelPipeline pipeline = nioSocketChannel.pipeline();

                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new StringDecoder());


                            pipeline.addLast(new UserServiceHandler());
                        }
                    });

        serverBootstrap.bind(ip, port).sync
                ();

    }

}
