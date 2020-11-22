package com.lagou.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;


//拦截器的设置
@Intercepts({  //这里这个大括号，也就是说 这里可以定义对个@Signature 对 对个地方进行拦截，都用这个拦截器。
        @Signature(type = StatementHandler.class,   // 指定拦截的那个接口
                method = "prepare",     // 指定  要拦截的接口的 中要拦截的方法
                args = {Connection.class, Integer.class})  // 指定  要拦截的接口的 中要拦截的方法 中的参数，按顺序，不要多，也不要少。如果方法重载，通过这个 来确定你要执行的方法。

})
public class MyPlugin implements Interceptor {

    /**
     * 拦截方法，只要别拦截的目标对象的目标方法别执行时，每次都会执行intercept方法
     *
     * @param invocation
     * @return
     * @throws Throwable
     */

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        System.out.println("对方法进行了增强");
        return invocation.proceed();  //执行原方法
    }

    /**
     * 主要为了把当前的拦截器生成代理存到拦截器链中
     *
     * @param
     * @return
     */
    @Override
    public Object plugin(Object target) {

        System.out.println("将要包装的目标对象：" + target);
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

        System.out.println("插件配置的初始化参数：" + properties);
    }
}
