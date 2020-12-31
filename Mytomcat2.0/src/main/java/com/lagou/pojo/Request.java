package com.lagou.pojo;

import java.io.IOException;
import java.io.InputStream;

/**
 * 把请求信息封装成Request对象(根据InputStream输入流封装)
 */
public class Request {

    private String method;   //请求方式，比如 GET/POST
    private String url;     // 例如

    private InputStream inputStream;


    public Request(InputStream inputStream) throws IOException {
        // 将传入的流 转化成字符串
        this.inputStream = inputStream;
        int count= 0 ;
        while (count== 0){
            count = inputStream.available();
        }

        byte[] bytes = new byte[count];
        inputStream.read(bytes);
        String inputStr = new String(bytes);

// 解析字符串
        // 解析第一行请求头信息
        String firstHeadStr = inputStr.split("\\n")[0];

        String[] strings = firstHeadStr.split(" ");

        this.method = strings[0];
        this.url = strings[1];

        System.out.println("====>>>method :" +this.method);
        System.out.println("====>>>url :" +this.url);


    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
