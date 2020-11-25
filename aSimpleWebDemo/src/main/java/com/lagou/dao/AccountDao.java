package com.lagou.dao;

import com.lagou.pojo.Account;

import java.sql.SQLException;

public interface AccountDao {

    // 在接口中，定义一个 根据 cardNo 查询 账户的 方法
    Account queryAccountCardNo(String cardNo) throws SQLException, Exception;

    // 在接口中，定义一个 根据 cardNo 修改 money 的方法
    int updateAccountByCardNo(Account account) throws Exception;

}
