package com.lagou.mapper;


import com.lagou.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Select("select * from t_article ")
    public List<Article> selectArticleByPage(@Param("page") Integer page);


}
