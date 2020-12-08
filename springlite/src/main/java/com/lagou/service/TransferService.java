package com.lagou.service;

public interface TransferService {

    void transfer(String fromCardNo, String toCardNo, Integer money) throws Exception;

}
