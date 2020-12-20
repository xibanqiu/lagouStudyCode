package com.lagou.mvcframework.annotaiton;


import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MySecurity {

    String[] value() default {};

}
