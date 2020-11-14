package com.lagou.mapper;

import com.lagou.pojo.Orders;
import com.lagou.pojo.User;

import java.io.IOException;
import java.util.List;

public interface OrdersMapper {

    public List<Orders> findOrderAndUser() throws IOException;



}
