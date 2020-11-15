package com.lagou.config;

import com.lagou.io.Resources;
import com.lagou.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder(){

        this.configuration = new Configuration();
    }

    public Configuration parse(InputStream inputStream) throws Exception {

        Document document = new SAXReader().read(inputStream);

        List<Element> propertyList = document.selectNodes("//property");

        Properties properties = new Properties();
        for (Element element : propertyList) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.put(name,value);
        }

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("user"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        configuration.setDataSource(comboPooledDataSource);

        List<Element> mapperList = document.selectNodes("//mapper");

        for (Element element : mapperList) {

            String resource = element.attributeValue("resource");

            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);

            InputStream resourcesAsStream = Resources.getResourcesAsStream(resource);

            xmlMapperBuilder.parse(resourcesAsStream);

        }

        return configuration;
    }

}
