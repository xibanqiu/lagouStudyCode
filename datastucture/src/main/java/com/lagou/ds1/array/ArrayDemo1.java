package com.lagou.ds1.array;

public class ArrayDemo1 {

    int[] nums = new int[8];

    public ArrayDemo1(){
        nums[0] = 3;
        nums[1] = 1;
        nums[2] = 2;
        nums[3] = 5;
        nums[4] = 4;
        nums[5] = 9;
    }

    public int get(int i){
        return nums[i];
    }

    public void update(int i,int num){
        nums[i] = num;
    }

    // 从中间插入
    public void insertMiddle(int p, int n) {
        for (int i =  nums.length -1 ; i >= p -1; i--) {
            if(nums[i] !=0){
                nums[i+1] = nums[i];
            }
        }
        nums[n-1] = p;
    }

    // 就数组复制到新的数组中
    public void resize(){
        int[] newNums = new int[nums.length * 2];
        System.arraycopy(nums,0,newNums,0,nums.length);
    }


    // 插入 索引外的数字
    public void insertOutOfBounds(int p,int n){
        resize();
        nums[p-1] = n;
    }


    // 删除
    public void deleteMiddle(int p){
        for (int i = p;i < nums.length ; i++){
            nums[i-1] = nums[i];
        }
    }


    // 打印
    public void display() {
        for (int num : nums) {
            System.out.println(num);
        }
    }

}
