package com.lagou.sqlSession;

import com.lagou.config.BoundSql;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {

        //1、得到链接
        Connection connection = configuration.getDataSource().getConnection();

        //2、转换sql
        String sqlText = mappedStatement.getSqlText();
        BoundSql boundSql = getBoundSql(sqlText);

        //3、设置预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        //4、设置参数
        String parameterType = mappedStatement.getParameterType();
        Class<?> parameterTypeClass = getParameterTypeClass(parameterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();

        for (int i = 0; i < parameterMappingList.size(); i++) {

            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();

            Field declaredField = parameterTypeClass.getDeclaredField(content);

            //暴力访问
            declaredField.setAccessible(true);

            Object o = declaredField.get(params[0]);

            preparedStatement.setObject(i+1,o);

        }

        //5、执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        //6、封装返回集
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getParameterTypeClass(resultType);

        ArrayList<Object> returnList = new ArrayList<>();

        while (resultSet.next()){

            Object o = resultTypeClass.newInstance();

            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {

                String columnName = metaData.getColumnName(i);
                Object object = resultSet.getObject(columnName);

                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName,resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,object);
            }

            returnList.add(o);
        }



        return (List<E>) returnList;
    }

    private  Class<?> getParameterTypeClass(String parameterType) throws Exception {
        if(parameterType == null){
            throw new RuntimeException("parameterType is not null");
        }

        return Class.forName(parameterType);
    }


    private BoundSql getBoundSql(String sqlText) {

        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);

        String parseSql = genericTokenParser.parse(sqlText);

        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);

        return boundSql;
    }
}

