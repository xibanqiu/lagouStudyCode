package com.lagou.service.impl;

import com.lagou.dao.AccountDao;
import com.lagou.dao.Impl.AccountDaoImpl;
import com.lagou.factory.BeanFactory;
import com.lagou.pojo.Account;
import com.lagou.service.TransferService;

public class TransferServiceImpl implements TransferService {

//    private AccountDao accountDao = new AccountDaoImpl();

    // 使用工厂  实例化 accountDao 对象
//    private AccountDao accountDao = (AccountDao) BeanFactory.getBean("accountDao");

    private AccountDao accountDao = null;

    // 提供一个set 的方法 供 反射注入
    public void setAccountDao(AccountDao accountDao){
        this.accountDao = accountDao;
    }


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
