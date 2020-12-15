package com.lagou.pojo;

public class QueryVo {

    private User user;
    private String index;

    @Override
    public String toString() {
        return "QueryVo{" +
                "user=" + user +
                ", index='" + index + '\'' +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
