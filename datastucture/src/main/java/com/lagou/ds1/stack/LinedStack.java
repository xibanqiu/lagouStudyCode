package com.lagou.ds1.stack;

public class LinedStack {

    Node head ;
    int size ;

    /***
     * 初始化
     * */
    public LinedStack() {
        this.head = null;
        int size ;
    }

    /**
     入栈
     @param node
     */
    public void push(Node node) {

        if(head == null){
            head = node;
        }else {
            node.next = head;
            head = node;
        }
        size ++;
    }


    /***
     * 出栈
     * **
     * @return Node
     * */
    public Node pop() {

        if(size == 0){
            return null;
        }else {

            Node popNode = this.head;
            head = head.next;
            size --;
            return popNode;
        }

    }


    public static void main(String[] args) {

        Node n1=new Node(3);
        Node n2=new Node(5);
        Node n3=new Node(1);
        Node n4=new Node(4);

        LinedStack ls=new LinedStack();
        ls.push(n1);
        ls.push(n2);
        ls.push(n3);
        ls.push(n4);

        System.out.println(ls.pop().value);
        System.out.println(ls.pop().value);
        System.out.println(ls.pop().value);
        System.out.println(ls.pop().value);
    }

}
