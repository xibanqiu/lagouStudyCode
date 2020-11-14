package com.lagou.mapper;

import com.lagou.pojo.Orders;
import com.lagou.pojo.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.io.IOException;
import java.util.List;

public interface OrdersMapper {


    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "total",column = "total"),
            @Result(property = "user",column = "uid" ,javaType = User.class,
            one = @One(select = "com.lagou.mapper.UserMapper.selectUserById"))

    })
    @Select("select * from orders")
    public List<Orders> findOrderAndUser() throws IOException;

    @Select("select * from orders where id = #{uid}")
    public List<Orders> findOrderByUid(Integer uid);

}
