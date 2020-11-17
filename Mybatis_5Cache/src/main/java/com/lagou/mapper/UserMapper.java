package com.lagou.mapper;

import com.lagou.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.mybatis.caches.redis.RedisCache;

import java.util.List;

@CacheNamespace(implementation = PerpetualCache.class)  //开启二级缓存
//@CacheNamespace(implementation = RedisCache.class)  //开启redis 二级缓存
public interface UserMapper {

    @Insert("insert into user (id,username) values(#{id},#{username})")
    public void addUser(User user);

    @Update("update user set username = #{username} where id = #{id}")
    public void updateUser(User user);

    @Select("select * from user")
    public List<User> selectAllUser();

    @Delete("delete from user where id = #{id}")
    public void deleteUser(Integer id);

    @Select("select * from user where id = #{id}")
    public User selectUserById(Integer integer);


    @Select("select * from user")
    public List<User> findAllUserAndRole();



}
