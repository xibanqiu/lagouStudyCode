package com.lagou.test;


import com.lagou.mapper.OrdersMapper;
import com.lagou.mapper.UserMapper;
import com.lagou.pojo.Orders;
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

        SqlSession sqlSession = build.openSession();


    }


    @Test
    public void test1(){
        SqlSession sqlSession = build.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setId(10);
        user.setUsername("haha");

        userMapper.addUser(user);

    }

    @Test
    public void test2(){
        SqlSession sqlSession = build.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        user.setId(10);
        user.setUsername("修改");

        userMapper.updateUser(user);

    }

    @Test
    public void test3(){
        SqlSession sqlSession = build.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);


        List<User> users = userMapper.selectAllUser();

        users.forEach(System.out::println);

    }

    @Test
    public void test4(){
        SqlSession sqlSession = build.openSession(true);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        userMapper.deleteUser(10);

    }

    @Test
    public void test5() throws IOException {
        SqlSession sqlSession = build.openSession(true);

        OrdersMapper ordersMapper = sqlSession.getMapper(OrdersMapper.class);


        List<Orders> orders = ordersMapper.findOrderAndUser();

        orders.forEach(System.out::println);


    }

    @Test
    public void test6() throws IOException {
        SqlSession sqlSession = build.openSession(true);

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        List<User> users = userMapper.findAll();
        users.forEach(System.out::println);
    }

    @Test
    public void test7() throws IOException {
        SqlSession sqlSession = build.openSession(true);

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        List<User> users = userMapper.findAllUserAndRole();
        users.forEach(System.out::println);
    }



}
