package com.lagou.ds1.line;


// 单链表
public class SingleLinkedList {

    // 头结点
    private Node head = new Node(0,"");


    public void addNode(Node node) {

        Node tmp = head;
        while ( tmp != null){
            tmp = tmp.nextNode;
        }
        tmp.nextNode = node;
    }


    // 根据ID来排序 插入
    public void addByIdOrder(Node node){

        Node tmp = head;

        while(  tmp.nextNode != null && tmp.nextNode.index <= node.index){
            if(tmp.nextNode.index == node.index){
                return;
            }
            tmp = tmp.nextNode;
        }

        node.nextNode = tmp.nextNode;
        tmp.nextNode = node;

    }


    //遍历链表
     public void showList() {

        if(head.nextNode == null){
            System.out.println("链表为空");
            return;
        }

         Node tmp = head.nextNode;

         while (tmp != null){
             System.out.println(tmp.name);
             tmp=tmp.nextNode;
        }

     }

    public static void main(String[] args) {

        Node n1=new Node(1,"张飞");
        Node n2=new Node(2,"关羽");
        Node n3=new Node(3,"赵云");
        Node n4=new Node(4,"黄忠");
        Node n5=new Node(5,"马超");

        SingleLinkedList sll=new SingleLinkedList();

        sll.addByIdOrder(n4);
        sll.addByIdOrder(n5);
        sll.addByIdOrder(n1);
        sll.addByIdOrder(n2);
        sll.addByIdOrder(n3);
        sll.showList();

    }

}
