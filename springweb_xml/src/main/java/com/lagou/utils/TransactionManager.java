package com.lagou.utils;

public class TransactionManager {

    private ConnectionUtils connectionUtils ;

    public void setConnectionUtils(ConnectionUtils connectionUtils ){

        this.connectionUtils = connectionUtils;

    }

    private static TransactionManager transactionManager = new TransactionManager();

    public static TransactionManager getInstance() {
        return  transactionManager;
    }



    // 开启手动事务控制
    public void beginTransaction() throws Exception {
        connectionUtils.getCurrentThreadConn().setAutoCommit(false);
    }


    // 提交事务
    public void commit() throws Exception {
        connectionUtils.getCurrentThreadConn().commit();
    }


    // 回滚事务
    public void rollback() throws Exception {
        connectionUtils.getCurrentThreadConn().rollback();
    }

}
