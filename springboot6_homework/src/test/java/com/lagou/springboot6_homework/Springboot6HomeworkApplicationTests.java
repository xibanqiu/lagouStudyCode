package com.lagou.springboot6_homework;


import com.lagou.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Springboot6HomeworkApplicationTests {

    @Autowired
    private ArticleService articleService;

    @Test
    public void test(){


        System.out.println(articleService.selectArticleByPage("5"));

    }

}
