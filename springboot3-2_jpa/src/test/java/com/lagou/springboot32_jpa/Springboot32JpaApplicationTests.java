package com.lagou.springboot32_jpa;

import com.lagou.CommentRepository;
import com.lagou.pojo.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class Springboot32JpaApplicationTests {

    @Autowired
    private CommentRepository repository;

    @Test public void selectComment() {

        Optional<Comment> optional = repository.findById(1);
        if(optional.isPresent()){
            System.out.println(optional.get());
        }
        System.out.println();
    }

}
