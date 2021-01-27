package com.lagou.entity;

import java.io.Serializable;

/** * 引用对象应该是可序列化对象，这样才能在远程调用的时候：
 * 1. 序列化对象
 * 2. 拷贝
 * 3. 在网络中传输 *
 * 4. 服务端反序列化
 * 5. 获取参数进行方法调用；
 * 这种方式其实是将远程对象引用传递的方式转化为值传递的方式 */
public class User implements Serializable {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
