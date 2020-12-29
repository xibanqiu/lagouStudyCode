package com.lagou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.mapper.ArticleMapper;
import com.lagou.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    private final Integer pageSize = 1;

    private Integer navigateLastPage = null;
    Integer findPage = null;

    public List<Article> selectArticleByPage(String page){

        switch (page){
            case "firstPage":
                findPage = 1;
                break;
            case "previewsPage":
                findPage = findPage-1;
                break;
            case "nextPage":
                findPage = findPage+1;
                break;
            case "lastPage":
                findPage = navigateLastPage;
                break;

        }

        System.out.println(findPage);
        PageHelper.startPage(findPage, pageSize);
        List<Article> articles = articleMapper.selectArticleByPages();

        PageInfo<Article> articlePageInfo = new PageInfo<>(articles);
        navigateLastPage = articlePageInfo.getNavigateLastPage();

        return articles;

    }

}
