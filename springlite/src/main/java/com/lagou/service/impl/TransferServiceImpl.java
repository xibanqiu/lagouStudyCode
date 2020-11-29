package com.lagou.service.impl;

import com.lagou.dao.AccountDao;
import com.lagou.dao.Impl.AccountDaoImpl;
import com.lagou.factory.BeanFactory;
import com.lagou.pojo.Account;
import com.lagou.service.TransferService;
import com.lagou.utils.TransactionManager;

public class TransferServiceImpl implements TransferService {

//    private AccountDao accountDao = new AccountDaoImpl();

    // 使用工厂  实例化 accountDao 对象
//    private AccountDao accountDao = (AccountDao) BeanFactory.getBean("accountDao");

    private AccountDao accountDao = null;

    // 提供一个set 的方法 供 反射注入
    public void setAccountDao(AccountDao accountDao){
        this.accountDao = accountDao;
    }

//    private TransactionManager transactionManager;
//
//    public void setTransactionManager(TransactionManager transactionManager){
//        this.transactionManager = transactionManager;
//    }

    @Override
    public void transfer(String fromCardNo, String toCardNo, Integer money) throws Exception {

//        try {
//            //开启事务管理
//            transactionManager.beginTransaction();

            Account from = accountDao.queryAccountCardNo(fromCardNo);
            Account to = accountDao.queryAccountCardNo(toCardNo);

            from.setMoney(from.getMoney() - money);
            to.setMoney(to.getMoney() + money);

            accountDao.updateAccountByCardNo(from);
            accountDao.updateAccountByCardNo(to);

//            //进行事务提交
//            transactionManager.commit();
//        }catch (Exception e){
//            //进行事务回滚
//            transactionManager.rollback();
//            throw  e;
//        }

    }
}
