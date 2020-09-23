package com.johann.springbootjdbctemplate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @Description:  主键注解
* @Param:  
* @return:  
* @Author: Johann 
* @Date: 2020/9/21 
*/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Pk {
    /** 
    * @Description: 自增主键
    * @Param: [] 
    * @return: boolean 
    * @Author: Johann 
    * @Date: 2020/9/23
    */ 
    boolean auto() default true;
}
