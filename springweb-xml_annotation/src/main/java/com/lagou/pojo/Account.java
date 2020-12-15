package com.lagou.pojo;

public class Account {

    String name;    // 用户名
    String cardNo;  // 账户金额
    Integer money;  // 银行卡号

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", money=" + money +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
