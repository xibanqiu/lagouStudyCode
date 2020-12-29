package com.lagou.mapper;

import com.lagou.pojo.Account;

import java.util.List;

public interface AccountMapper {

    // 定义dao层接⼝⽅法--> 查询account表所有数据
    List<Account> queryAccountList() throws Exception;


}
