package com.lagou.service;

import com.lagou.mapper.AccountMapper;
import com.lagou.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;


    public List<Account> queryAccountList() throws Exception {

        return  accountMapper.queryAccountList();
    }
}
