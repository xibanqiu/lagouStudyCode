package com.lagou.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class UserClientHandler extends ChannelInboundHandlerAdapter implements Callable {


    private ChannelHandlerContext context;


    private String result ;
    private String param ;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;
    }


    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        result = msg.toString();

        notify();
    }

    @Override
    public synchronized Object call() throws Exception {

        context.writeAndFlush(param);
        wait();
        return result;
    }

    //5.设置参数的方法
    public void setParam(String param){
        this.param = param;
    }
}
