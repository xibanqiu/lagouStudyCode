package com.lagou.ds1.line;

// 双链表
public class Node2 {


    Integer id;

    String name;

    Node2 next;

    Node2 prev;

    public Node2(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Node2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
