package com.lagou.dao.Impl;

import com.lagou.dao.AccountDao;
import com.lagou.pojo.Account;
import com.lagou.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDaoImpl implements AccountDao {

    private ConnectionUtils connectionUtils= null;

    public void setConnectionUtils (ConnectionUtils connectionUtils){
        this.connectionUtils = connectionUtils;
    }


    @Override
    public Account queryAccountCardNo(String cardNo) throws Exception {

        Connection connection = connectionUtils.getCurrentThreadConn();

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
//        connection.close();

        return  account;
    }

    @Override
    public int updateAccountByCardNo(Account account) throws Exception{

        Connection connection = connectionUtils.getCurrentThreadConn();

        String sql = "update account set money = ? where cardNo = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1,account.getMoney());
        preparedStatement.setString(2,account.getCardNo());

        int i = preparedStatement.executeUpdate();

        preparedStatement.close();
//        connection.close();

        return i;
    }
}
