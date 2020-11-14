package com.lagou.dao;

import com.lagou.pojo.SimpleUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public List<SimpleUser> findAll() throws IOException {

        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = build.openSession();

        List<SimpleUser> users = sqlSession.selectList("userMapper.findAll");

        return users;
    }

    /**
     * 下面的实现类似 不做实现
     *
     */
    @Override
    public void insertUser(SimpleUser user) throws IOException {

    }

    /**
     * 下面的实现类似 不做实现
     *
     */
    @Override
    public void deleteUser(Integer id) throws IOException {

    }


    /**
     * 下面的实现类似 不做实现
     *
     */
    @Override
    public void updateUser(SimpleUser user) throws IOException {

    }


}
