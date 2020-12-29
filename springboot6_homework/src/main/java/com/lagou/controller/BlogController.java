package com.lagou.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.pojo.Article;
import com.lagou.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class BlogController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/hello/{page}")
    public String firstPage(Model model,@PathVariable("page") String page){

        List<Article> articles = articleService.selectArticleByPage(page);

        model.addAttribute("articles",articles);
        model.addAttribute("page",page);

        return "client/index";
    }


}
