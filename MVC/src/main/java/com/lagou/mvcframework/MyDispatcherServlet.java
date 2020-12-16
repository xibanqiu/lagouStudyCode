package com.lagou.mvcframework;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class MyDispatcherServlet extends HttpServlet {

    // 配置一个
    private Properties properties = new Properties();

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // 1 、加载配置文件 获取一些配置的信息，如包扫描的路径
             // 这里为了简单 使用 将 配置写在 properties 文件中
        String contextConfigLocation = servletConfig.getInitParameter("contextConfigLocation");
        doLoadConfig(contextConfigLocation);


        // 2、扫描相关的类，扫描注解
        doScan(properties.getProperty("mvc.scan"));


        // 3、 初始化bean对象 （实现ioc容器，基于注解）

        // 4、实现依赖注入

        // 5、 构造一个HandlerMapping ,将配置好的url 和 Method 建立映射关系


        System.out.println("my mvc build successful!!");

    }

    //缓存扫描到的类 全限定名
    private List<String> classNames = new ArrayList<>();

    private void doScan(String scanPackage) {

        String path =Thread.currentThread().getContextClassLoader().getResource("").getPath()+ scanPackage.replace("\\.","/");

        File pack = new File(path);

        File[] files = pack.listFiles();

        for (File file : files) {
            // 如果是文件夹 递归
            if(file.isDirectory()){
                doScan(scanPackage + file.getName());

            }else if(file.getName().endsWith(".class")){
                String className = scanPackage + "." +file.getName().replaceAll(".class","");
                classNames.add(className);
            }

        }


    }




    private void doLoadConfig(String contextConfigLocation) {

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);

        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


}
