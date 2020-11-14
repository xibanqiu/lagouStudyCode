package com.lagou.mapper;

import com.lagou.pojo.SimpleUser;

import java.io.IOException;
import java.util.List;

public interface UserMapper {

    public List<SimpleUser> findAll() throws IOException;

    public List<SimpleUser> findByIf(SimpleUser user) throws Exception;
    public List<SimpleUser> findByIds(Integer[] ids) throws Exception;

    public void insertUser(SimpleUser user) throws IOException;
    public void deleteUser(Integer id) throws IOException;
    public void updateUser(SimpleUser user) throws IOException;
}
