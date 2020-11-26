package com.lagou.utils;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author 应癫
 */
public class DruidUtils {

    private DruidUtils(){
    }

    private static DruidDataSource druidDataSource = new DruidDataSource();


    static {
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl(" jdbc:mysql://rm-bp1k5i9g0iyt2jc79o.mysql.rds.aliyuncs.com:3306/test?useSSL=false");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("zxcasdqwe123!@#");

    }

    public static DruidDataSource getInstance() {
        return druidDataSource;
    }

}
