package com.lagou.utils;

public class HttpProcotolUtil {
    private static String str404 = "<h1>404 Not Found</h1>";


    public static String getHttpHead200(long contentLength){

        return "HTTP/1.1 200 OK \n"+
                "Content-Type:text/html \n"+
                "Content-Length"+contentLength+"\n"+
                "\r\n";
    }


    public static String getHttpHead404(){

        return "HTTP/1.1 404 NOT FOUND\n"+
                "Content-Type:text/html\n"+
                "Content-Length"+str404.length()+"\n"+
                "\r\n"+str404;
    }


}
