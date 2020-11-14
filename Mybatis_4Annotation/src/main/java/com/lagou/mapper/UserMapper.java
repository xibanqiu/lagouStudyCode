package com.lagou.mapper;

import com.lagou.pojo.Orders;
import com.lagou.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

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
    public User selectUserById();


    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "birthday",column = "birthday"),
            @Result(property = "ordersList", column = "id",javaType = List.class,
            many = @Many(select = "com.lagou.mapper.OrdersMapper.findOrderByUid")),
    })
    @Select("select * from user")
    public List<User> findAll();


    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "password",column = "password"),
            @Result(property = "birthday",column = "birthday"),
            @Result(property = "roleList", column = "id",javaType = List.class,
                    many = @Many(select = "com.lagou.mapper.RoleMapper.findRoleByUid")),

    })
    @Select("select * from user")
    public List<User> findAllUserAndRole();



}
