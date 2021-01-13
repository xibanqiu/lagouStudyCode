package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author : zhengyao3@郑瑶
 * @date : 2020/6/8 17:08
 * @Description: 输出静态文件工具类
 */
public class StaticResourceUtil {

    /**
     * 获取静态资源文件的绝对路径
     * @param path
     * @return
     */
    public static String getAbsolutePath(String path) {
        //获取静态文件地址
        String absolutePath = StaticResourceUtil.class.getResource("/").getPath();
        return absolutePath.replaceAll("\\\\", File.separator) + path;
    }

    /**
     * 将静态文件从输入流中读取到输出流
     * @param fileInputStream
     * @param outputStream
     * @throws IOException
     */
    public static void outputStaticResource(FileInputStream fileInputStream, OutputStream outputStream) throws IOException {
        int  resourceSize= fileInputStream.available();
        // 输出http请求头,然后再输出具体内容
        outputStream.write(HttpProtocolUtil.getHttpHeader200(resourceSize).getBytes());
        //将文件中读取到的字节返回到前台
        byte[] bytes = fileInputStream.readAllBytes();
        outputStream.write(bytes);
    }
}
