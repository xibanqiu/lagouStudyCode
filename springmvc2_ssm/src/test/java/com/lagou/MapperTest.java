package com.lagou;


import com.lagou.mapper.AccountMapper;
import com.lagou.pojo.Account;
import com.lagou.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@ContextConfiguration(locations = "classpath*:application*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MapperTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void test1() throws Exception {

        List<Account> accounts = accountService.queryAccountList();

        accounts.forEach(System.out::println);



    }

}
