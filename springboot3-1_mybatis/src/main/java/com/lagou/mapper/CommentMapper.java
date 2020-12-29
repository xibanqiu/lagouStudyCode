package com.lagou.mapper;

import com.lagou.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface CommentMapper {

    @Select("SELECT * FROM t_comment WHERE id =#{id}")
    public Comment findById(Integer id);

}
