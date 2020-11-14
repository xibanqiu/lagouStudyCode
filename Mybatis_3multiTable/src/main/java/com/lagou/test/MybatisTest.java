package com.lagou.test;


import com.lagou.mapper.OrdersMapper;
import com.lagou.mapper.UserMapper;
import com.lagou.pojo.Orders;
import com.lagou.pojo.Role;
import com.lagou.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {

    private static InputStream  inputStream = null;
    private static SqlSessionFactory build = null;

    @BeforeAll
    public static void BeforeTest() throws IOException {
        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        build = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test1() throws Exception {

        SqlSession sqlSession = build.openSession();
        OrdersMapper mapper = sqlSession.getMapper(OrdersMapper.class);
        List<Orders> orders = mapper.findOrderAndUser();
        orders.forEach(System.out::println);

    }

    @Test
    public void test2() throws Exception {

        SqlSession sqlSession = build.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.findAll();

        users.forEach(System.out::println);

    }
    @Test
    public void test3() throws Exception {

        SqlSession sqlSession = build.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        List<User> users = userMapper.findAllUserAndRole();

        users.forEach(System.out::println);
    }


}
