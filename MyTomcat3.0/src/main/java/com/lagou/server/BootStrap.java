package com.lagou.server;

import com.lagou.pojo.Request;
import com.lagou.pojo.Response;
import com.lagou.servlet.HttpServlet;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BootStrap {

    private int port = 8082;

    private Map<String, HttpServlet> servletMap = new HashMap<String,HttpServlet>();   // servlet 容器

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

    /**
     * My Tomcat 3.0
     * @throws IOException
     */
    public void start() throws Exception {

        loadServlet();

        ServerSocket serverSocket = new ServerSocket(port);

        while (true){

            Socket socket = serverSocket.accept();
            Request request = new Request(socket.getInputStream());
            Response response = new Response(socket.getOutputStream());

            // 静态资源
            if(servletMap.get(request.getUrl()) == null){
                response.outHtml(request.getUrl());
            }else {
                HttpServlet httpServlet = servletMap.get(request.getUrl());
                httpServlet.service(request,response);
            }
            socket.close();
        }


    }

    public static void main(String[] args) {

        BootStrap bootStrap = new BootStrap();

        try {
            bootStrap.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
