package com.lagou.factory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {

    // 容器
    private static Map<String,Object> map = new HashMap<>();

    static {

        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");

        try {

            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(resourceAsStream);

            //得到根元素
            Element rootElement = document.getRootElement();

            List<Element> beanList = rootElement.selectNodes("//bean");

            for (int i = 0; i < beanList.size(); i++) {

                Element element = beanList.get(i);

                String id = element.attributeValue("id");
                String className = element.attributeValue("class");

                Class<?> aClass = Class.forName(className);

                Object o = aClass.newInstance();

                map.put(id,o);
            }

            List<Element> propertyList = rootElement.selectNodes("//property");

            for (int i = 0; i < propertyList.size(); i++) {

                Element element = propertyList.get(i);
                String name = element.attributeValue("name");
                String ref = element.attributeValue("ref");

                Element parentElement = element.getParent();

                String parentId = parentElement.attributeValue("id");

                Object parentObj = map.get(parentId);

                Method[] methods = parentObj.getClass().getMethods();
                for (Method method : methods) {

                    if(  ( "set"+name).equals(method.getName() )  ){

                        Object propertyObj = map.get(ref);
                        method.invoke(parentObj,propertyObj);
                    }

                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static Object getBean(String idName){

       return map.get(idName);

    }

}
