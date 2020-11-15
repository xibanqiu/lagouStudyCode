package com.lagou.test;

import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class MybatisTest {


    @Test
    public void  test() throws Exception {

        InputStream inputStream = Resources.getResourcesAsStream("SqlMapConfig.xml");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        List<User> users = sqlSession.selectAll("UserMapper.selectAll");

        users.forEach(System.out::println);

        User user = new User();

        user.setId(1);
        user.setUsername("zhangsan");

        User user1 = sqlSession.selectOne("UserMapper.selectOne", user);

        System.out.println(user1);

    }


}
