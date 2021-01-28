package com.lagou.handler;

import com.lagou.service.UserServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class UserServiceHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 如何符合规定，则调用本地方法，返回数据
        if(msg.toString().startsWith("UserService")){
            String result = new UserServiceImpl()
                    .sayHello(msg.toString().substring(msg.toString().lastIndexOf("#")));

            ctx.writeAndFlush(result);
        }


    }
}
