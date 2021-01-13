package server;

import classloader.MyClassLoader;
import component.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BootstrapV4 {
    /**
     * 默认监听8080端口
     */
    private int port = 8080;
    private Map<String, HttpServlet> servletMap = new HashMap<String, HttpServlet>();
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 100, 100, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000));
    private Server server;

    public void start() throws Exception {
        server = loadServer();
        loadServlet(server);
        //监听
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("启动Minicat2.0成功,监听端口为:" + port);
        while (true) {
            Socket socket = serverSocket.accept();
            threadPoolExecutor.execute(() -> {
                try (socket) {
                    System.out.println("收到访问请求!");
                    InputStream inputStream = socket.getInputStream();
                    Request request = new Request(inputStream,server);
                    
                    Response response = new Response(socket.getOutputStream());
                
                    // 静态资源处理
                    if (request.getMappingData().getWrapper() == null) {
                        response.outPutStaticFile(request.getUrl());
                    } else {
                        // 动态资源servlet请求
                        Servlet servlet = request.getMappingData().getWrapper().getServlet();
                        servlet.service(request, response);
                    }
                    socket.close();
                    System.out.println("返回结果!");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }

    /**
     * 获取tomcat下的配置文件,将配置文件进行解析,
     */
    private Server loadServer() {
        Server tempServer = new Server();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("server.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();

            Node server = rootElement.selectSingleNode("//Server");
            List<Element> serviceList = server.selectNodes("//Service");
            serviceList.forEach(item -> {
                Connector tempConnector = getConnector(item);
                Mapper mapper = new Mapper();
                Node engine = item.selectSingleNode("//Engine");
                Engine tempEngine = getEngine(mapper);
                setService(tempConnector, tempEngine, tempServer);
                setHostList(mapper, engine);
            });
            return tempServer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Engine getEngine(Mapper mapper) {
        Engine tempEngine = new Engine();
        tempEngine.setMapper(mapper);
        return tempEngine;
    }

    private void setService(Connector tempConnector, Engine tempEngine, Server server) {
        Service service = new Service();
        service.setConnector(tempConnector);
        service.setEngine(tempEngine);
        service.setName("");
        server.addService(service);
    }

    private void setHostList(Mapper mapper, Node engine) {
        //Host有多个,根据name来分组,用List来接
        List<Element> hostList = engine.selectNodes("//Host");
        hostList.forEach(host -> {
            String name = host.attributeValue("name");
            String appBase = host.attributeValue("appBase");
            //将Host属性封装到Host对象中
            Host tempHost = new Host();
            tempHost.setName(name);
            tempHost.setAppBase(appBase);
            mapper.addHost(tempHost);
            List<Element> contextList = host.selectNodes("//Context");
            contextList.forEach(context -> {
                String contextName = context.attributeValue("name");
                String contextPath = context.attributeValue("path");
                //将Context属性封装到Context属性中
                Context tempContext = new Context();
                tempContext.setName(contextName);
                tempContext.setPath(contextPath);
                tempHost.addContext(tempContext);
            });
        });
    }

    private Connector getConnector(Element item) {
        //Connector只有一个
        Element connector = (Element) item.selectSingleNode("//Connector");
        String port = connector.attributeValue("port");
        Connector tempConnector = new Connector();
        this.port = Integer.valueOf(port);
        tempConnector.setPort(this.port);
        tempConnector.setName(connector.attributeValue("name"));
        return tempConnector;
    }

    /**
     * 加载解析web.xml，初始化Servlet
     */
    private void loadServlet(Server server) {
        server.getServiceList().forEach(service ->{
            service.getEngine().getMapper().getHosts().forEach(host -> {
                String appBase = host.getAppBase();
                host.getContexts().forEach(context ->{
                    String name = context.getName();
                    String path = context.getPath();
                    String usrDir = System.getProperty("user.dir")+File.separator+"tomcat_learn";
                    String appDir = usrDir+File.separator+appBase+path;
                    //获取各个web应用的web.xml配置文件
                    String realPath = appDir+File.separator+"src"+File.separator+
                            "main"+File.separator+"resources"+File.separator+"web.xml";
                    realPath = realPath.replaceAll("\\\\","/");

                    File file = new File(realPath);
                    InputStream resourceAsStream = null;
                    try {
                        resourceAsStream = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        throw  new RuntimeException("未找到相应工程的配置文件!");
                    }
                    SAXReader saxReader = new SAXReader();

                    try {
                        Document document = saxReader.read(resourceAsStream);
                        Element rootElement = document.getRootElement();

                        List<Element> selectNodes = rootElement.selectNodes("//servlet");
                        for (int i = 0; i < selectNodes.size(); i++) {
                            Element element = selectNodes.get(i);
                            // <servlet-name>lagou</servlet-name>
                            Element servletnameElement = (Element) element.selectSingleNode("servlet-name");
                            String servletName = servletnameElement.getStringValue();
                            // <servlet-class>server.LagouServlet</servlet-class>
                            Element servletclassElement = (Element) element.selectSingleNode("servlet-class");
                            String servletClass = servletclassElement.getStringValue();
                            // 根据servlet-name的值找到url-pattern
                            Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet" +
                                    "-name='" + servletName + "']");
                            // /lagou
                            String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
                            appDir = appDir.replaceAll("\\\\","/");
                            Class servletClazz = MyClassLoader.getClassFromJavaFile(appDir, servletClass);
                            Wrapper wrapper = new Wrapper();
                            wrapper.setName(urlPattern);
                            wrapper.setServlet((Servlet) servletClazz.newInstance());
                            context.addWrapper(wrapper);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                
            });
        });
    }

    public static void main(String[] args) throws Exception {
        BootstrapV4 bootstrap = new BootstrapV4();
        bootstrap.start();
    }
}
