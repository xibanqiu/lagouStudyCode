package com.lagou.service;

import com.lagou.mapper.ArticleMapper;
import com.lagou.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    public List<Article> selectArticle(Integer page){

        return articleMapper.selectArticleByPage(page);

    }

}
