package com.lagou.test;

import com.lagou.mapper.UserMapper;
import com.lagou.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PluginTest {


    @Test
    public void test() throws IOException {

        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = build.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);


        List<User> users = mapper.selectUsers();

        users.forEach(System.out::println);


    }

}
