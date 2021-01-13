package server;

import component.MappingData;
import component.Server;
import enums.HttpMethod;

import java.io.IOException;
import java.io.InputStream;


public class Request {
    /**
     * 方法类型
     */
    private HttpMethod method;
    /**
     * url
     */
    private String url;
    /**
     * 请求的输入流
     */
    private InputStream inputStream;

    /**
     * 获取mappingData
     */
    private MappingData mappingData = new MappingData();

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
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

    public MappingData getMappingData() {
        return mappingData;
    }

    public void setMappingData(MappingData mappingData) {
        this.mappingData = mappingData;
    }

    /**
     * 根据输入流进行初始化设置
     * @param inputStream
     */
    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        // 从输入流中获取请求信息
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        //将请求信息转为字符串
        String requestValue = new String(bytes);
        //按照换行符来切割,只获取第一行信息即可
        String[] requestValues = requestValue.split("\n");
        //将第一行信息按照空格来进行切割,获取前两个参数
        String[] httpValue = requestValues[0].split(" ");
        String method = httpValue[0];
        String url = httpValue[1];
        this.method = HttpMethod.valueOf(method);
        this.url = url;
    }

    public Request(InputStream inputStream, Server server) throws IOException {
        this.inputStream = inputStream;
        // 从输入流中获取请求信息
        int count = 0;
        int index = 0 ;
        while (count == 0) {
            index ++;
            count = inputStream.available();
            if (index > 500) {
                return;
            }
                    
        }
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        //将请求信息转为字符串
        String requestValue = new String(bytes);
        //按照换行符来切割,只获取第一行信息即可
        String[] requestValues = requestValue.split("\n");
        //将第一行信息按照空格来进行切割,获取前两个参数
        String[] httpValues = requestValues[0].split(" ");
        String[] hostValues = requestValues[1].split(":");
        String method = httpValues[0];
        String url = httpValues[1];
        //第一个/到第二个/默认为context名
        int first = url.indexOf("/");
        int second = url.indexOf("/",2);
        if(first >= 0 && second > 0){
            //获取host名称
            String host = hostValues[1].trim();
            //获取应用名称
            String contextName = url.substring(first, second);
            //第二个开始默认为servlet名
            String servletName = url.substring(second);
            setMappingData(server, host, contextName, servletName);
            this.method = HttpMethod.valueOf(method);
        }
    }

    private void setMappingData(Server server, String host, String contextName, String servletName) {
        server.getServiceList().forEach(service -> {
            service.getEngine().getMapper().getHosts().forEach(serverHost ->{
                if (serverHost.getName().equals(host)) {
                    mappingData.setHost(serverHost);
                    serverHost.getContexts().forEach(context -> {
                        if (context.getName().equals(contextName)) {
                            mappingData.setContext(context);
                            context.getWrappers().forEach(wrapper -> {
                                if (wrapper.getName().equals(servletName)) {
                                    mappingData.setWrapper(wrapper);
                                }
                            });
                        }
                    });
                }
            });
        });
    }
}
