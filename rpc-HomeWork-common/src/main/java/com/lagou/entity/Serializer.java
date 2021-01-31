package com.lagou.entity;

public interface Serializer {

    byte[] serialize(Object object) throws Exception;

    <T> T deserialize(Class<T> clazz, byte[] bytes) throws Exception;
}
