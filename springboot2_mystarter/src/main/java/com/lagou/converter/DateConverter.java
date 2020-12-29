package com.lagou.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换器
 * converter<S,T>
 *     S:source 源类型
 *     T：Target:目标类型
 *
 */
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {

        System.out.println(111);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date parse = simpleDateFormat.parse(source);

            return parse;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
