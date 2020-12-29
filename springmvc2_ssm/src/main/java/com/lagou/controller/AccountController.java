package com.lagou.controller;


import com.lagou.pojo.Account;
import com.lagou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
@RequestMapping("/account")
public class AccountController {
    /**
     * Spring容器和SpringMVC容器是有层次的（⽗⼦容器）
     * Spring容器：service对象+dao对象
     * SpringMVC容器：controller对象，，，，可以引⽤到Spring容器中的对象
     */
    @Autowired
    private AccountService accountService;

    @RequestMapping("/queryAll")
    @ResponseBody
    public List<Account> queryAll() throws Exception {
        return accountService.queryAccountList();
    }

}