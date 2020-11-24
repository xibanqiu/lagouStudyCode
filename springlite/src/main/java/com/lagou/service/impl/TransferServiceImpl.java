package com.lagou.service.impl;

import com.lagou.dao.AccountDao;
import com.lagou.dao.Impl.AccountDaoImpl;
import com.lagou.pojo.Account;
import com.lagou.service.TransferService;

public class TransferServiceImpl implements TransferService {

    private AccountDao accountDao = new AccountDaoImpl();

    @Override
    public void transfer(String fromCardNo, String toCardNo, Integer money) throws Exception {

        Account from = accountDao.queryAccountCardNo(fromCardNo);
        Account to = accountDao.queryAccountCardNo(toCardNo);

        from.setMoney(from.getMoney() - money);
        to.setMoney(to.getMoney() + money);

        accountDao.updateAccountByCardNo(from);
        accountDao.updateAccountByCardNo(to);
    }
}
