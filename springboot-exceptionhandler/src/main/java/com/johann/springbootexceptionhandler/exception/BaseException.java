package com.johann.springbootexceptionhandler.exception;

import com.johann.springbootexceptionhandler.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName: BaseException
 * @Description: 异常基类
 * @Author: Johann
 * @Date: 2020-09-17 16:42
 **/
@Data
@EqualsAndHashCode(callSuper = true)
//注解在类上；为类提供一个无参的构造方法
@NoArgsConstructor
//注解在类上；为类提供一个全参的构造方法
@AllArgsConstructor
public class BaseException extends RuntimeException{

    private Integer code;

    private String message;

    public BaseException(Status status){
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();
    }

//    public BaseException(Integer code,String message){
//        super(message);
//        this.code = code;
//        this.message = message;
//    }
}
