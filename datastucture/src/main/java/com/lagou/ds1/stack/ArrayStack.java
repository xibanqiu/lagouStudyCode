package com.lagou.ds1.stack;

public class ArrayStack {

    int[] arr ;
    int count;


    public ArrayStack(int n) {
        this.arr = new int[n];
        this.count = 0;
    }


    // 入栈操作
     public boolean push(int n) {

        if(count >= arr.length) return false;

        arr[count] = n;
        count ++;
        return true;
     }

    // 出栈操作
    public int pop() {

        if(count == 0) return 0;

        return arr[count-- - 1];
    }


    public static void main(String[] args) {
        ArrayStack as=new ArrayStack(8);
        as.push(3);
        as.push(5);
        as.push(1);
        as.push(4);

        System.out.println(as.pop());
        System.out.println(as.pop());
        System.out.println(as.pop());
        System.out.println(as.pop());
    }

}
