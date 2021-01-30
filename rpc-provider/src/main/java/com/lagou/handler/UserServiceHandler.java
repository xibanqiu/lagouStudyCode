package com.lagou.handler;

import com.lagou.service.UserServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class UserServiceHandler extends ChannelInboundHandlerAdapter {

    // 当客户端读取数据是，该方法会被调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 注意：客户端将来发送请求的时候回传递一个参数  UserService#sayHello#are you ok
        //  判断当时的当前的请求是否 符合规则
        if(msg.toString().startsWith("UserService")){
            //2.如果符合规则,调用实现类货到一个result
            UserServiceImpl service = new UserServiceImpl();
            String result = service.sayHello(msg.toString().substring(msg.toString().lastIndexOf("#")+1));
            //3.把调用实现类的方法获得的结果写到客户端
            ctx.writeAndFlush(result);
        }

    }
}
