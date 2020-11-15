package com.lagou.sqlSession;

import java.util.List;

public interface SqlSession {

    public <T> T selectOne(String statementId,Object ... params) throws Exception;

    public <T> List<T> selectAll(String statementId, Object ... params) throws Exception;

    public <T> T getMapper(Class<?> mapperClass) throws Exception;


}
