package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory{


    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
