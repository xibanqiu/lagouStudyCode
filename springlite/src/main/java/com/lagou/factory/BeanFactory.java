package com.lagou.factory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 解析配置文件
    static {
        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        try {
            // dom4j 加载 配置文件 ,与 xpath 表达式 解析文件
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(resourceAsStream);
            //得到根元素
            Element rootElement = document.getRootElement();

            //获取 根元素下的 所有 子 元素 bean
            List<Element> list = rootElement.selectNodes("//bean");
            // 对每一个 <bean> 做解析工作
            for (int i = 0; i < list.size(); i++) {
                Element element = list.get(i);
                String idStr = element.attributeValue("id");
                String classStr = element.attributeValue("class");

                // 通过反射 实例化对象
                Class<?> aClass = Class.forName(classStr);
                Object o = aClass.newInstance();
                map.put(idStr,o);

            }

            List<Element> properties = rootElement.selectNodes("//property");

            for (int i = 0; i < properties.size(); i++) {
                Element element = properties.get(i);

                String ref = element.attributeValue("ref");
                String classStr = element.attributeValue("class");

                


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 提供一个 外部获取 bean的方法
    public static Object getBean(String id)  {

       return map.get(id);
    }

}
