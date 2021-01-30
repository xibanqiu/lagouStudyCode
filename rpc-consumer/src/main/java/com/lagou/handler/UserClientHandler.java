package com.lagou.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class UserClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    // 1、定义成员变量
    private ChannelHandlerContext context; // 事件处理器上下文对象(存储handler信息，写操作)

    private String result;    // 记录服务器返回的数据
    private String param;     // 记录将要传送给 服务器的数据

    // 2、 实现channelActive 客户端和服务区连接时 ，该方法自动执行
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        this.context = ctx;
    }

    // 3、实现channelRead 当我们读到服务器数据，该方法自动执行
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        result = msg.toString();

        notify();

    }

    // 4、将客户端的数据写的服务器
    public synchronized Object call() throws InterruptedException {
        context.writeAndFlush(param);
        wait();
        return result;
    }

    //5.设置参数的方法
    public void setParam(String param){
        this.param = param;
    }



}
