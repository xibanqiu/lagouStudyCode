package com.lagou.mvcframework.servlet;

import com.lagou.mvcframework.annotaiton.MyAutowired;
import com.lagou.mvcframework.annotaiton.MyController;
import com.lagou.mvcframework.annotaiton.MyRequestMapping;
import com.lagou.mvcframework.annotaiton.MyService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


public class MyDispatcherServlet extends HttpServlet {

    // 配置一个
    private Properties properties = new Properties();

    //缓存扫描到的类 全限定名
    private List<String> classNames = new ArrayList<>();

    // 创建一个ioc 容器
    private Map<String,Object> ioc = new HashMap<>();

    // 创建要给  url 和  方法  的映射容器
    private Map<String, Method> handleMapping = new HashMap<>();

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        // 1 、加载配置文件 获取一些配置的信息，如包扫描的路径
             // 这里为了简单 使用 将 配置写在 properties 文件中
        String contextConfigLocation = servletConfig.getInitParameter("contextConfigLocation");
        doLoadConfig(contextConfigLocation);

        // 2、扫描相关的类，扫描注解
        doScan(properties.getProperty("mvc.scan"));

        // 3、 初始化bean对象 （实现ioc容器，基于注解）
        doInstance();

        // 4、实现依赖注入
        doAutoWired();

        // 5、 构造一个HandlerMapping ,将配置好的url 和 Method 建立映射关系
        initHandleMapping();


        System.out.println("my mvc build successful!!");

    }

    private void initHandleMapping() {

        if(ioc.isEmpty()) return;

        // 维护 ioc 容器类之间的关系
        try{
            for ( Map.Entry<String,Object> entry : ioc.entrySet()){

                Class<?> aClass = entry.getValue().getClass();

                // 判断是否 ioc容器是否 包含 MyRequestMapping 注解
                if ( aClass.isAnnotationPresent(MyRequestMapping.class) ){
                    MyRequestMapping annotation = aClass.getAnnotation(MyRequestMapping.class);
                    String baseUrl = annotation.value();

                    Method[] methods = aClass.getMethods();

                    for (Method method : methods) {
                        if (method.isAnnotationPresent(MyRequestMapping.class)){

                            MyRequestMapping methodAnnotation = method.getAnnotation(MyRequestMapping.class);

                            String methodUrl = methodAnnotation.value();

                            String url = baseUrl + methodUrl;

                            handleMapping.put(url,method);

                        }

                    }


                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void doAutoWired() {

        if(ioc.isEmpty()) return;

        // 维护 ioc 容器类之间的关系
        try{
            for ( Map.Entry<String,Object> entry : ioc.entrySet()){

                // 得到该实例的所有属性，遍历
                Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
                for (Field declaredField : declaredFields) {

                    // 判断 是否 MyAutowired
                    if( declaredField.isAnnotationPresent(MyAutowired.class) ){
                        MyAutowired annotation = declaredField.getAnnotation(MyAutowired.class);

                        String beaName = declaredField.getName();
                        if( StringUtils.isBlank(annotation.value()) ){
                            beaName = annotation.value();
                        }
                        Object o = ioc.get(beaName);

                        // 暴力访问
                        declaredField.setAccessible(true);
                        
                        declaredField.set(entry.getValue(),ioc.get(beaName));
                        
                    }

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void doInstance(){
        if ( classNames.size() == 0 ) return;

        try {

            for (int i = 0; i < classNames.size(); i++) {
                Class<?> aClass = Class.forName(classNames.get(i));


                // MyController 的处理方式 : 根据Class 实例化 对象,将首字母小写 放到 Map集合中
                if( aClass.isAnnotationPresent(MyController.class) ){
                    Object instance = aClass.newInstance();
                    ioc.put(changeFirstCharLower(classNames.get(i)),instance);

                }

                // MyService 的处理方式
                // 一 、判断是否有指定的value,
                //              如果有，则容器的key为指定的value值 ,
                //              如果没有，则将容器的key为类名首字母小写

                // 二、判断是否有实现的接口，如果有将 接口的类名首字母小写作为key，本身class 也存一份到 ioc 容器中
                if( aClass.isAnnotationPresent(MyService.class) ){
                    Object instance = aClass.newInstance();
                    MyService annotation = aClass.getAnnotation(MyService.class);
                    String value = annotation.value();

                    // 判断 注解的value 是否为空
                    if(StringUtils.isBlank(value)){

                        ioc.put(changeFirstCharLower(classNames.get(i)),instance);

                    }else {

                        ioc.put(value,instance);
                    }

                    // 判断是否实现了接口
                    Class<?>[] interfaces = aClass.getInterfaces();
                    if(interfaces.length > 0){

                        for (Class<?> anInterface : interfaces) {
                            String simpleName = anInterface.getSimpleName();

                            ioc.put(changeFirstCharLower(simpleName),instance);
                        }
                    }

                    ioc.put(changeFirstCharLower(classNames.get(i)),instance);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private void doScan(String scanPackage) {

        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + scanPackage.replace(".","/");

        File pack = new File(path);

        File[] files = pack.listFiles();

        for (File file : files) {
            // 如果是文件夹 递归
            if(file.isDirectory()){
                doScan(scanPackage + "/"+ file.getName());

            }else if(file.getName().endsWith(".class")){

//                String className = scanPackage + "." + file.getName().replaceAll(".class", "");
//                classNames.add(className);

                String className = scanPackage + "." +file.getName().replaceAll(".class","");
                classNames.add(className.replace("/","."));
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


    private String changeFirstCharLower(String name){


        if( null == name) return name;

        char[] chars = name.toCharArray();

        if('A' < chars[0]  && chars[0] < 'Z') {

            chars[0]  = (char) (chars[0] - 32);

        }

        return chars.toString();

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestURI = req.getRequestURI();
        Method method = handleMapping.get(requestURI);
        method.invoke();


    }


}
