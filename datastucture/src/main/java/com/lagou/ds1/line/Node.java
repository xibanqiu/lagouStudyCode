package com.lagou.ds1.line;

public class Node {

    Integer index;

    String name;

    // 下一个节点
    Node nextNode;


    public Node(Integer index, String name) {
        this.index = index;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", nextNode=" + nextNode +
                '}';
    }
}
