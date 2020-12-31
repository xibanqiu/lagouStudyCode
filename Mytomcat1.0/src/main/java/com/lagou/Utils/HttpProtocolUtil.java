package com.lagou.Utils;

/**
 * http 协议工具类，主要是提供 响应头对应的信息，这里我们只提供 200 和 404 的情况
 */
public class HttpProtocolUtil {


    /**
     * 设置 请求为 200 的 head信息
     * @param contentLength
     * @return
     */
    public static String getHttpHead200(int contentLength){

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
        String str404 = "<h1>404 not found</h1>";

        return "HTTP/1.1 404 NOT Found \n"+
                "Content-Type:text/html \n"+
                "Content-Length"+str404+"\n"+
                "\r\n" + str404;
    }


}
