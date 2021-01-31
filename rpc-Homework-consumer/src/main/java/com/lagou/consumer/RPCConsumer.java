package com.lagou.consumer;


import com.lagou.handler.UserClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RPCConsumer {

    private static ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static UserClientHandler userClientHandler;

    public static void initClient() throws InterruptedException {

        userClientHandler = new UserClientHandler();

        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

                        ChannelPipeline pipeline = nioSocketChannel.pipeline();

                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());

                        pipeline.addLast(userClientHandler);

                    }
                });

        //5)连接服务端
        bootstrap.connect("127.0.0.1",8999).sync();
    }



    public static Object createProxy(Class<?> serviceClass, final String providerParam){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        if( userClientHandler == null){
                            initClient();
                        }

                        userClientHandler.setParam(providerParam+objects[0]);

                        Object result = executorService.submit(userClientHandler).get();

                        return result;
                    }
                });
    }



}
