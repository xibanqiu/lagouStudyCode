package utils;

/**
 * @author : zhengyao3@郑瑶
 * @date : 2020/6/8 16:39
 * @Description: 返回Http请求头的封装类
 */
public class HttpProtocolUtil {
    /**
     * 为响应码200提供请求头信息
     * @return
     */
    public static String getHttpHeader200(int contentLength) {
        return "HTTP/1.1 200 OK \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + contentLength + " \n" +
                "\r\n";
    }

    /**
     * 为响应码404提供请求头信息(此处也包含了数据内容)
     * @return
     */
    public static String getHttpHeader404() {
        String str404 = "<h1>404 not found</h1>";
        return "HTTP/1.1 404 NOT Found \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + str404.getBytes().length + " \n" +
                "\r\n" + str404;
    }
}
