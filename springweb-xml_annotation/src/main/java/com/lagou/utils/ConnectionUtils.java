package com.lagou.utils;

import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component
public class ConnectionUtils {

    private static ThreadLocal<Connection> threadLocal= new ThreadLocal<>();

    public Connection getCurrentThreadConn()throws Exception{

        Connection connection = threadLocal.get();
        if(null == connection){

            connection = DruidUtils.getInstance().getConnection();
            threadLocal.set(connection);

        }

        return connection;
    }

}
