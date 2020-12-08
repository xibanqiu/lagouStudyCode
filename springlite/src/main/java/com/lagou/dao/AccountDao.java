package com.lagou.dao;

import com.lagou.pojo.Account;


public interface AccountDao {

    Account queryAccountCardNo(String cardNo) throws Exception;

    int updateAccountByCardNo(Account account) throws Exception;

}
