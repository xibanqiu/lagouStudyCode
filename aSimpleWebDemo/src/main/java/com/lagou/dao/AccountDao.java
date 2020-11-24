package com.lagou.dao;

import com.lagou.pojo.Account;

import java.sql.SQLException;

public interface AccountDao {

    Account queryAccountCardNo(String cardNo) throws SQLException, Exception;

    int updateAccountByCardNo(Account account) throws Exception;

}
