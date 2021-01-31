package com.lzy.service;

import com.alibaba.fastjson.JSON;

public class JsonSerializer implements Serializer{
    @Override
    public byte[] serialize(Object object) throws Exception {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) throws Exception {
        return JSON.parseObject(bytes, clazz);
    }
}
