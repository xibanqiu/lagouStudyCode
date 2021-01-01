package com.lagou.server;

import com.lagou.servlet.HttpServlet;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class BootStrap {

    private int port = 8084;  // 端口

    private Map<String, HttpServlet> servletMap = new HashMap<>();

    public void start() throws Exception {

        ServerSocket serverSocket = new ServerSocket(port);
        loadServlet();

//        int corePoolSize,
//        int maximumPoolSize,
//        long keepAliveTime,
//        TimeUnit unit,
//        BlockingQueue<Runnable> workQueue,
//                ThreadFactory
//        threadFactory,
//                RejectedExecutionHandler handler

        int corePoolSize = 10;
        int maximumPoolSize = 50 ;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,threadFactory,handler);


        while (true){
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket, servletMap);

            // 多线程 1 ===>不使用 线程池
//            requestProcessor1.start();

            // 多线程 2 ===>使用 线程池
            threadPoolExecutor.execute(requestProcessor);
        }

    }

    public void loadServlet(){
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");

        SAXReader saxReader = new SAXReader();

        try{
            Document document = saxReader.read(resourceAsStream);

            Element rootElement = document.getRootElement();

            List<Element> servlets = rootElement.selectNodes("//servlet");

            for (int i = 0; i < servlets.size(); i++) {

                Element element = servlets.get(i);

                // <servlet-name>lagou</servlet-name>
                Element servletNameEle = (Element)element.selectSingleNode("servlet-name");
                String servletName= servletNameEle.getTextTrim();

                //    <servlet-class>com.lagou.server.lagouServlet</servlet-class>
                Element servletClassEle = (Element)element.selectSingleNode("servlet-class");
                String servletClass= servletClassEle.getTextTrim();


                // 根据 servletName 找到 url-pattern
                Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" +servletName + "']");
                String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();

                servletMap.put(urlPattern,(HttpServlet) Class.forName(servletClass).newInstance());

            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void main(String[] args)  {

        BootStrap bootStrap = new BootStrap();

        try {
            bootStrap.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
