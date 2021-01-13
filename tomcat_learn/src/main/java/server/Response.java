package server;

import utils.HttpProtocolUtil;
import utils.StaticResourceUtil;

import java.io.*;


public class Response {
    private OutputStream outputStream;

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    public void outPutStaticFile(String path) throws Exception {
        // 获取静态资源文件的绝对路径
        String absoluteResourcePath = StaticResourceUtil.getAbsolutePath(path);

        // 输入静态资源文件
        File file = new File(absoluteResourcePath);
        if(file.exists() && file.isFile()) {
            // 读取静态资源文件，输出静态资源
            StaticResourceUtil.outputStaticResource(new FileInputStream(file),outputStream);
        }else{
            // 输出404
            output(HttpProtocolUtil.getHttpHeader404());
        }
    }

    public void output(String httpHeader404) throws IOException {
        outputStream.write(httpHeader404.getBytes());
    }
}
