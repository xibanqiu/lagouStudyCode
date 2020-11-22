package com.lagou.test;

import com.lagou.mapper.UserMapper;
import com.lagou.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class CacheTest {

    private UserMapper userMapper;
    private  SqlSession sqlSession ;
    private  SqlSessionFactory build  ;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        build = new SqlSessionFactoryBuilder().build(resourceAsStream);

        sqlSession = build.openSession();

        userMapper = sqlSession.getMapper(UserMapper.class);

    }


    @Test
    public void test(){


        //第一次查询
        User user1 = userMapper.selectUserById(1);

        //更新用户,刷新缓存
//        User user = new User();
//        user.setId(1);
//        user.setUsername("tom");
//        sqlSession.commit();

        //手动更新，缓存
        sqlSession.clearCache();

        //第二次查询
        User user2 = userMapper.selectUserById(1);

        System.out.println(user2 == user1);
    }


    @Test
    public void test2(){

        SqlSession sqlSession1 = build.openSession();
        SqlSession sqlSession2 = build.openSession();
        SqlSession sqlSession3 = build.openSession();

        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        UserMapper mapper3 = sqlSession3.getMapper(UserMapper.class);

        User user1 = mapper1.selectUserById(1);

        sqlSession1.close();

//        User user = new User();
//        user.setId(1);
//        user.setUsername("TTT");
//        mapper3.updateUser(user);
//        sqlSession3.commit();

        User user2 = mapper2.selectUserById(1);

        System.out.println(user1 == user2);

    }




}
