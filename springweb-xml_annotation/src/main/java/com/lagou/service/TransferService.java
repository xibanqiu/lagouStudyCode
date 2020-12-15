package com.lagou.service;

public interface TransferService {

    /**
     *
     * // 定义一个 转账的方法
     * @param fromCardNo  转账方
     * @param toCardNo   被转账方
     * @param money      转账金额
     * @throws Exception
     */
    void transfer(String fromCardNo, String toCardNo, Integer money) throws Exception;

}
