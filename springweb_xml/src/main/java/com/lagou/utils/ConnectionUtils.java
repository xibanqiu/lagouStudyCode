package com.lagou.utils;

import java.sql.Connection;
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
