package com.lagou.io;

import java.io.InputStream;

public class Resources {

    /**
     * 工具类
     *      利用ClassLoader 将 配置文件转成 InputStream
     * @param path
     * @return
     */
    public static InputStream getResourcesAsStream(String path){
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }

}
