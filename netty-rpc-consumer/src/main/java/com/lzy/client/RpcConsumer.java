package com.lzy.client;

import com.lzy.handler.UserClientHandler;
import com.lzy.service.JsonSerializer;
import com.lzy.service.RpcEncoder;
import com.lzy.service.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcConsumer {

    public static ExecutorService executorService
            = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static UserClientHandler userClientHandler;

    public static void initClient() throws InterruptedException {
        userClientHandler = new UserClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new RpcEncoder(RpcRequest.class, new JsonSerializer()));
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(userClientHandler);
                    }
                });
        bootstrap.connect("127.0.0.1", 8999).sync();
    }

    public static Object createProxy(Class<?> serverClass) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serverClass}, (proxy, method, args) -> {
                    if (userClientHandler == null) {
                        initClient();
                    }
                    RpcRequest rpcRequest = new RpcRequest();
                    rpcRequest.setRequestId(UUID.randomUUID().toString());
                    rpcRequest.setMethodName(method.getName());
                    rpcRequest.setClassName("com.lzy.service.UserServiceImpl");
                    rpcRequest.setParameters(args);
                    rpcRequest.setParameterTypes(method.getParameterTypes());
                    userClientHandler.setParam(rpcRequest);
                    System.out.println("rpcRequest = " + rpcRequest);
                    System.out.println("客户端设置参数完成");
                    return executorService.submit(userClientHandler).get();
                });
    }
}
