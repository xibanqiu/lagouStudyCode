package com.lagou.dao;

import com.lagou.pojo.SimpleUser;

import java.io.IOException;
import java.util.List;

public interface UserDao {

    public List<SimpleUser> findAll() throws IOException;
    public void insertUser(SimpleUser user) throws IOException;
    public void deleteUser(Integer id) throws IOException;
    public void updateUser(SimpleUser user) throws IOException;

}
