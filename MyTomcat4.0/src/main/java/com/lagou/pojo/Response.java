package com.lagou.pojo;

import com.lagou.utils.HttpProcotolUtil;
import com.lagou.utils.StaticResourceUtil;

import java.io.*;

public class Response {

    private OutputStream outputStream;


    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void outputHtml(String path) throws Exception {

        String absolutePath = StaticResourceUtil.getAbsolutePath(path);

        File file = new File(absolutePath);

        if(file.exists() && file.isFile()){

            StaticResourceUtil.outputStaticResource2(new FileInputStream(file),outputStream);
        }else {
            outputContent(HttpProcotolUtil.getHttpHead404());
        }
    }




    public void outputContent(String content) throws IOException {
        outputStream.write(content.getBytes());
    }

}
