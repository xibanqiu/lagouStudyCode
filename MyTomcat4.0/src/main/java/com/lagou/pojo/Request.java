package com.lagou.pojo;

import java.io.IOException;
import java.io.InputStream;

public class Request {

    private String url ;
    private String method;

    private InputStream inputStream;

    public Request(InputStream inputStream) throws IOException {

        int count = 0;
        while (count == 0){
            count = inputStream.available();
        }

        byte[] bytes = new byte[count];

        inputStream.read(bytes);

        String inputStr = new String(bytes);

        String firstContent = inputStr.split("\\n")[0];

        String[] split = firstContent.split(" ");

        this.method = split[0];
        this.url = split[1];

        System.out.println("====>>>method :" +this.method);
        System.out.println("====>>>url :" +this.url);

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

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
