package com.lagou.pojo;

import java.io.File;
import java.io.OutputStream;

/**
 * 封装Response 对象 ，需要依赖于OutputStream
 * 该对象 需要提供 核心方法 输出html
 */
public class Response {

    private OutputStream outputStream;

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     *
     *
     * @param path
     */
    public void outputHtml(String path){
        // 获取静态资源文件的绝对路径
        String absoluteResourcePath = "";

        File file = new File(path);
        if(file.exists()){

        }else {



        }


    }


}
