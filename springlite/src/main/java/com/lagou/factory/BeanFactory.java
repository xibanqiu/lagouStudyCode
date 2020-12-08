package com.lagou.factory;

import com.lagou.annotation.MyAutowired;
import com.lagou.annotation.MyService;
import com.lagou.annotation.MyTransaction;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.reflections.Reflections;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class BeanFactory {

    /**
     * 工程类 的两个任务
     *
     * 任务一、加载 解析xml ，读取想xml 中的bead的 信息，通过反射的技术 实例化bean对象，然后放入容器中 待用
     * 任务二、提供接口方法根据id从 容器中获取 bean （静态方法）
     *
     */

    // 声明一个容器，存储实例化对象
    private static Map<String,Object> map = new HashMap<>();

    public static String inputPath = "";




    // 解析配置文件
    static {

        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        try {
            // dom4j 加载 配置文件 ,与 xpath 表达式 解析文件
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(resourceAsStream);
            //得到根元素
            Element rootElement = document.getRootElement();

            //获取 根元素下的 所有 子 元素 component-scan
            List<Element> list = rootElement.selectNodes("//component-scan");

            // 放置 需要 扫描的包路径的集合
            List<String> scanPathList = new ArrayList<>();
            // 解析 Element 得到所有的path ,放置到集合中
            for (Element element : list) {
                String value = element.attributeValue("base-package");
                scanPathList.add(value);
            }

            //遍历集合，开始 扫描包
            for (String path : scanPathList) {

                // 调用第三方 jar
                Reflections reflections = new Reflections(path);
                //得到 包路径下的 带有 MyService注解的类
                Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(MyService.class);

                //遍历
                for (Class<?> aClass : typesAnnotatedWith) {

                    // 实例化
                    Object bean = aClass.newInstance();
                    // 得到注解
                    MyService annotation = aClass.getAnnotation(MyService.class);

                    // 判断使用注解是否有value 的值 的时候 有没有
                    if (StringUtils.isBlank(annotation.value())) {
                        String[] split = aClass.getName().split("\\.");
                        map.put(split[split.length - 1], bean);
                    } else {
                        map.put(annotation.value(), bean);
                    }
                }

                // 实例化完成之后维护对象的依赖关系，检查哪些对象需要传值进入，根据它的配置，我们传入相应的值
                for (Map.Entry<String, Object> a : map.entrySet()) {
                    Object o = a.getValue();
                    Class c = o.getClass();
                    //获取属性集合
                    Field[] fields = c.getDeclaredFields();
                    //遍历属性，若持有Autowired注解则注入
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(MyAutowired.class)
                                && field.getAnnotation(MyAutowired.class).required()) {
//                            String[] names = field.getType().getName().split("\\.");
                            String name = field.getName();
                            Method[] methods = c.getMethods();
                            for (int j = 0; j < methods.length; j++) {
                                Method method = methods[j];
                                if (method.getName().equalsIgnoreCase("set" + name)) {  // 该方法就是 setAccountDao(AccountDao accountDao)
                                    method.invoke(o, map.get(name));
                                }
                            }
                        }
                    }

                    // 判断对象是否 有 Transational 注解，若有 则修改对象为代理对象
                    if(c.isAnnotationPresent(MyTransaction.class)){
                        // 得到代理工厂
                        ProxyFactory proxyFactory = (ProxyFactory)BeanFactory.getBean("proxyFactory");
                        // 得到接口的数组
                        Class<?>[] interfaces = c.getInterfaces();

                        // 判断对象是否实现接口
                        if(interfaces != null && interfaces.length != 0){
                            // 该类实现接口 ，使用 JDK
                            o = proxyFactory.getObjByJdkProxy(o);

                        }else {
                            // 该类没有实现接口 ，使用 cglib
                            o = proxyFactory.getObjByCglibProxy(o);
                        }
                    }

                    // 把处理之后的object重新放到map中
                    map.put(a.getKey(),o);

                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    // 解析配置文件
//    static {
//        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
//        try {
//            // dom4j 加载 配置文件 ,与 xpath 表达式 解析文件
//            SAXReader saxReader = new SAXReader();
//            Document document = saxReader.read(resourceAsStream);
//            //得到根元素
//            Element rootElement = document.getRootElement();
//
//            //获取 根元素下的 所有 子 元素 bean
//            List<Element> list = rootElement.selectNodes("//bean");
//            // 对每一个 <bean> 做解析工作
//            for (int i = 0; i < list.size(); i++) {
//                Element element = list.get(i);
//                String idStr = element.attributeValue("id");
//                String classStr = element.attributeValue("class");
//
//                // 通过反射 实例化对象
//                Class<?> aClass = Class.forName(classStr);
//                Object o = aClass.newInstance();
//                map.put(idStr,o);
//
//            }
//
//            // 获取 根元素下的 所有 子 元素  property
//            List<Element> properties = rootElement.selectNodes("//property");
//
//            for (int i = 0; i < properties.size(); i++) {
//
//                // 获取当前的 element 及 它的属性
//                Element element = properties.get(i);
//
//                String ref = element.attributeValue("ref");
//                String name = element.attributeValue("name");
//
//                // 获取该元素 的父元素
//                String parentId = element.getParent().attributeValue("id");
//
//                // 容器中获取 父元素的 实例
//                Object  parentObj= map.get(parentId);
//
//                // 变量 父元素实例 的所有方法
//                Method[] methods = parentObj.getClass().getMethods();
//                for (int j = 0; j < methods.length; j++) {
//
//                    Method method = methods[j];
//
//                    // 父元素方法 有一个 为  set + 本类类名的方法(如：setAccount)时， 执行注入操作。
//                    if( ("set"+name).equals(method.getName()) ){
//                        Object propertyObj = map.get(ref);
//                        method.invoke(parentObj,propertyObj);
//
//                    }
//                }
//                //重新放入 容器中
//                map.put(parentId,parentObj);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    // 提供一个 外部获取 bean的方法
    public static Object getBean(String id)  {

       return map.get(id);
    }

}
