package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {


    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws Exception {

        Document document = new SAXReader().read(inputStream);

        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");


        List<Element> list = document.selectNodes("//select");

        for (Element element : list) {

            String id = element.attributeValue("id");
            String parameterType = element.attributeValue("parameterType");
            String resultType = element.attributeValue("resultType");

            String sqlText = element.getText();

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSqlText(sqlText);
            String key = namespace + "." + id;

            configuration.getMappedStatementMap().put(key,mappedStatement);

        }

    }

}
