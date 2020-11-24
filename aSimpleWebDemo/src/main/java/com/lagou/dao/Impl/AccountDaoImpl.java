package com.lagou.dao.Impl;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.lagou.dao.AccountDao;
import com.lagou.pojo.Account;
import com.lagou.utils.DruidUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDaoImpl implements AccountDao {


    @Override
    public Account queryAccountCardNo(String cardNo) throws Exception {

        DruidPooledConnection connection = DruidUtils.getInstance().getConnection();

        String sql = "select * from account where cardNo = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,cardNo);

        ResultSet resultSet = preparedStatement.executeQuery();

        Account account = new Account();

        while (resultSet.next()){

            account.setMoney(resultSet.getInt("money"));
            account.setCardNo(resultSet.getString("cardNo"));
            account.setName(resultSet.getString("name"));

        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return  account;
    }

    @Override
    public int updateAccountByCardNo(Account account) throws Exception{

        DruidPooledConnection connection = DruidUtils.getInstance().getConnection();

        String sql = "update account set money = ? where cardNo = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1,account.getMoney());
        preparedStatement.setString(2,account.getCardNo());

        int i = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        return i;
    }
}
