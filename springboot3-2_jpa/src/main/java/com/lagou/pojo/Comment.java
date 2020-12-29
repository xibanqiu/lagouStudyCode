package com.lagou.pojo;


import javax.persistence.*;

@Entity
public class Comment {

    @Id  //表明映射对应的主键id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //设置主键自增策略
    private Integer id;
    private String content;
    private String author;

    @Column(name = "a_id")   // 指定 映射的表 字段名
    private Integer aid;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", aid=" + aid +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }
}
