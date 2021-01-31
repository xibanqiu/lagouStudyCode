package com.lzy.handler;

import com.lzy.service.RpcRequest;
import com.lzy.service.UserServiceImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;
//import org.springframework.beans.BeansException;
//import org.springframework.cglib.reflect.FastClass;
//import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//@Component
public class UserServiceHandler extends ChannelInboundHandlerAdapter  {


//    private static ApplicationContext applicationContext;
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        UserServiceHandler.applicationContext = applicationContext;
//    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest rpcRequest = (RpcRequest) msg;

        Object handler = handler(rpcRequest);
        ctx.writeAndFlush("success");

    }

    private Object handler(RpcRequest rpcRequest) throws Exception {

        Class<?> clazz = Class.forName(rpcRequest.getClassName());

        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();

        Method method = clazz.getMethod(methodName,parameterTypes);

        return method.invoke(clazz.newInstance(), parameters[0]);
    }

}
