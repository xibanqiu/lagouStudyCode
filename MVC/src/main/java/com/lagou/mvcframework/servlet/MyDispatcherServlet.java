package com.lagou.mvcframework.servlet;

import com.lagou.mvcframework.annotaiton.*;
import com.lagou.mvcframework.pojo.Handler;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Pattern;


public class MyDispatcherServlet extends HttpServlet {

    // 配置一个
    private Properties properties = new Properties();

    //缓存扫描到的类 全限定名
    private List<String> classNames = new ArrayList<>();

    // 创建一个ioc 容器
    private Map<String,Object> ioc = new HashMap<>();

    // 创建要给  url 和  方法  的映射容器
    private ArrayList<Handler> handleMapping = new ArrayList<>();

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

                    boolean flag_security = aClass.isAnnotationPresent(MySecurity.class);  // 该类是否实现了@MySecurity

                    String[] MySecurityValue = null;

                    if(flag_security){
                        MySecurity MySecurityAnnotation = aClass.getAnnotation(MySecurity.class);
                        MySecurityValue = MySecurityAnnotation.value();
                    }

                    Method[] methods = aClass.getMethods();

                    for (Method method : methods) {
                        if (method.isAnnotationPresent(MyRequestMapping.class)){

                            MyRequestMapping methodAnnotation = method.getAnnotation(MyRequestMapping.class);
                            String methodUrl = methodAnnotation.value();
                            String url = baseUrl + methodUrl;

                            Handler handler = new Handler(entry.getValue(),method,Pattern.compile(url));

                            // 得到方法的 参数
                            Parameter[] parameters = method.getParameters();

                            for (int i = 0; i < parameters.length; i++) {

                                Parameter parameter = parameters[i];
                                //方法的参数 是 HttpServletResponse 和 HttpServletRequest
                                if(parameter.getType() == HttpServletResponse.class || parameter.getType() == HttpServletRequest.class){

                                    handler.getParamIndexMapping().put(parameter.getType().getSimpleName(),i);
                                }else {
                                    handler.getParamIndexMapping().put(parameter.getName(),i);
                                }

                            }

                            // 类实现了 @MySecurity
                            if(flag_security){
                                handler.setMySecurityClass(MySecurityValue);

                                // 判断方法实现了没有
                                if(method.isAnnotationPresent(MySecurity.class)){

                                    MySecurity MySecurityAnnotationClass = method.getAnnotation(MySecurity.class);
                                    handler.setMySecurityMethod(MySecurityAnnotationClass.value());

                                }

                            }

                            handleMapping.add(handler);

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

                        String besaName = declaredField.getName();
                        if( StringUtils.isNotBlank(annotation.value()) ){
                            besaName = annotation.value();
                        }
                        Object o = ioc.get(besaName);

                        // 暴力访问
                        declaredField.setAccessible(true);

                        declaredField.set(entry.getValue(),ioc.get(besaName));

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
                    ioc.put(changeFirstCharLower(aClass.getSimpleName()),instance);

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

            chars[0]  = (char) (chars[0] + 32);

        }

        return String.valueOf(chars);

    }

    private Handler getHandler(HttpServletRequest request){

        if(handleMapping.isEmpty()) return null;

        String url = request.getRequestURI();

        // 遍历得到 url 匹配的handler
        for (Handler handler : handleMapping) {

            if(handler.getPattern().matcher(url).matches()){
                return handler;
            }
        }

        return null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Handler handler = getHandler(req);

        if(null == handler){
            resp.getWriter().write("404 not found");
            return;

        }

        Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
        Object[] paramValues = new Object[parameterTypes.length];

        Map<String, String[]> parameterMap = req.getParameterMap();

        for(Map.Entry<String, String[]> param : parameterMap.entrySet()){

            String value = StringUtils.join(param.getValue(), ",");

            if("name".equals(param.getKey())){
                // 判断不包含
                if(  handler.getMySecurityClass().length !=0 && !Arrays.asList(handler.getMySecurityClass()).contains(value)){

                    resp.setCharacterEncoding("utf-8");
                    resp.setHeader("Content-Type", "text/html;charset=utf-8");
//                    resp.setContentType("text/plain;charset=utf-8");

                    resp.getWriter().write("该用户没有权限访问类");
                    return;
                }
            }

            if("name".equals(param.getKey())){
                // 判断不包含
                if(  handler.getMySecurityMethod().length !=0 && !Arrays.asList(handler.getMySecurityMethod()).contains(value)){

                    resp.setCharacterEncoding("utf-8");
                    resp.setHeader("Content-Type", "text/html;charset=utf-8");
//                    resp.setContentType("text/plain;charset=utf-8");

                    resp.getWriter().write("该用户没有权限访问方法");
                    return;
                }
            }

            // 如果不包含 跳出
            if(!handler.getParamIndexMapping().keySet().contains(param.getKey())){continue;}

            Integer index = handler.getParamIndexMapping().get(param.getKey());

            paramValues[index] = value;
        }


        Integer requestIndex = handler.getParamIndexMapping().get(HttpServletRequest.class.getSimpleName());
        paramValues[requestIndex] = req;

        Integer responseIndex = handler.getParamIndexMapping().get(HttpServletResponse.class.getSimpleName());
        paramValues[responseIndex] = resp;


        try {
            handler.getMethod().invoke(handler.getController(),paramValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


}
