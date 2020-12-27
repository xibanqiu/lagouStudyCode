package com.lagou.springboot32_jpa;

import com.lagou.CommentRepository;
import com.lagou.pojo.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Springboot32JpaApplicationTests {

    @Autowired
    private CommentRepository repository;

    @Test
    public void selectComment() {

        Optional<Comment> optional = repository.findById(1);
        if(optional.isPresent()){
            System.out.println(optional.get());
        }
        System.out.println();
    }

}
