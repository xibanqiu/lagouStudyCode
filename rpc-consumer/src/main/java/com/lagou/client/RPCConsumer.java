package com.lagou.client;


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

//消费者
public class RPCConsumer {

    // 一、创建一个线程池对象 --- 它要处理 我们自定义的事件
    private static ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // 二、声明一个 自动应以事件处理器 userClientHandler
    private static UserClientHandler userClientHandler ;

    // 三、 编写方法，初始化客户端 (创建连接池 bootStrap 设置bootStrap ,连接服务器)
    public static void initClient() throws InterruptedException {

        // 1） 初始化UserClientHandler
       userClientHandler = new UserClientHandler();

        // 2）创建连接池对象
        NioEventLoopGroup group = new NioEventLoopGroup();

        // 3）创建客户端 的引导对象
        Bootstrap bootstrap = new Bootstrap();

        // 4）配置引导对象
        bootstrap.group(group)
                //设置通道为NIO
                .channel(NioSocketChannel.class)
                //设置请求协议为TCP
                .option(ChannelOption.TCP_NODELAY,true)
                //监听channel 并初始化
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

                        ChannelPipeline pipeline = nioSocketChannel.pipeline();

                        // 设置编码
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());

                        // 添加自定义事件 处理器
                        pipeline.addLast(userClientHandler);

                    }
                });

        //5)连接服务端
        bootstrap.connect("127.0.0.1",8999).sync();
    }

    // 四、编写一个 方法 ，事件JDK 的动态代理创建对象
    // serviceClass 接口类型，根据 那个接口生成子类代理对象 ;   providerParam :  "UserService#sayHello#"
    public static  Object createProxy(Class<?> serviceClass, final String providerParam){

        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

                        //1) 初始化 客户端
                        if( userClientHandler == null){
                            initClient();
                        }
                        //2)给UserClientHandler 设置param参数
                        userClientHandler.setParam(providerParam+objects[0]);

                        //3).使用线程池,开启一个线程处理处理call() 写操作,并返回结果
                        Object result = executorService.submit(userClientHandler).get();

                        return result;
                    }
                });
    }


}
