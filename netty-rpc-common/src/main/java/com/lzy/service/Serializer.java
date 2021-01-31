package com.lzy.service;

public interface Serializer {

    byte[] serialize(Object object) throws Exception;

    <T> T deserialize(Class<T> clazz, byte[] bytes) throws Exception;
}
