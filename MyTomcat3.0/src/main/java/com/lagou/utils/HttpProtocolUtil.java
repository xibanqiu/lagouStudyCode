package com.lagou.utils;

public class HttpProtocolUtil {

    private static String str404 = "<h1>404 not found</h1>";

    public static String getHttpHead200(long contentLength){

        return "HTTP/1.1 200 OK \n"+
                "Content-Type:text/html \n"+
                "Content-Length"+contentLength+"\n"+
                "\r\n";
    }


    /**
     * 设置 请求为 404 的 head信息

     * @return
     */
    public static String getHttpHead404(){

        return "HTTP/1.1 404 NOT Found \n"+
                "Content-Type:text/html \n"+
                "Content-Length"+str404.length()+"\n"+
                "\r\n" + str404;
    }


}


