package com.johann.springbootjdbctemplate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @Description: 列注解
* @Param:  
* @return:  
* @Author: Johann 
* @Date: 2020/9/21 
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Column {
    /** 
    * @Description: 列名称
    * @Param: [] 
    * @return: java.lang.String 
    * @Author: Johann 
    * @Date: 2020/9/23
    */ 
    String name();
}
