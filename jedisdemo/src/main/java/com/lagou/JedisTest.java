package com.lagou;

import redis.clients.jedis.Jedis;

public class JedisTest {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("127.0.0.1", 6379,10000);

        jedis.set("name","zhangfei");

        System.out.println(jedis.get("name"));

        jedis.lpush("list1","1","2","3");

        System.out.println(jedis.rpop("list1"));

    }

}
