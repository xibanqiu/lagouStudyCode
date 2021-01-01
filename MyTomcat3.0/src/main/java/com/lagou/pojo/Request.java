package com.lagou.pojo;

import java.io.IOException;
import java.io.InputStream;

public class Request {

    private InputStream inputStream;
    private String url;
    private String method;

    public Request(InputStream inputStream) throws IOException {

        int count = 0;
        while (count == 0){
            count = inputStream.available();
        }

        byte[] bytes = new byte[count];

        inputStream.read(bytes);

        String inputStr = new String(bytes);
        System.out.println("inputStr===>>"+inputStr);
// 解析字符串
        // 解析第一行请求头信息
        String firstHeadStr = inputStr.split("\\n")[0];

        System.out.println("firstHeadStr===>>"+firstHeadStr);
        String[] strings = firstHeadStr.split(" ");

        this.method = strings[0];
        this.url = strings[1];

        System.out.println("====>>>method :" +this.method);
        System.out.println("====>>>url :" +this.url);

    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    
    
    
    
}
