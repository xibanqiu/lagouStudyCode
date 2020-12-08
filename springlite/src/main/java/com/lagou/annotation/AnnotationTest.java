package com.lagou.annotation;

import java.lang.reflect.Field;

public class AnnotationTest {

    @MyAutowired1(num = 4)
    private static int a ;

    public static void main(String[] args) throws IllegalAccessException {

        Field[] declaredField = AnnotationTest.class.getDeclaredFields();
        for (Field field : declaredField) {

            field.setAccessible(true);

            boolean annotationPresent = field.isAnnotationPresent(MyAutowired1.class);
            if(annotationPresent){

                MyAutowired1 annotation = field.getAnnotation(MyAutowired1.class);
                field.set(annotation,annotation.num());
            }

            System.out.println(a);
        }

    }


}
