package com.lagou.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;

/**

 */
public class DruidUtils {

    //实现单例
    private DruidUtils(){
    }
    private static DruidDataSource druidDataSource = new DruidDataSource();




    static {
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");  // mysql 驱动
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/bank");   // 连接的数据库
        druidDataSource.setUsername("root");                          // 数据库的用户名
        druidDataSource.setPassword("root");                          // 数据的密码

    }

    public static DruidDataSource getInstance() {
        return druidDataSource;
    }

}
