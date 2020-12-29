package com.lagou.demo.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Student {

    @Value("${person.id}")
    private Integer id;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                '}';
    }
}
