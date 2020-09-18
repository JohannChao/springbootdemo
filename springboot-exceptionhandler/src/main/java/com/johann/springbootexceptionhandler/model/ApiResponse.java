package com.johann.springbootexceptionhandler.model;

import com.johann.springbootexceptionhandler.constant.Status;
import com.johann.springbootexceptionhandler.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: ApiResponse
 * @Description: TODO
 * @Author: Johann
 * @Date: 2020-09-17 16:23
 **/
@Data
//注解在类上；为类提供一个无参的构造方法
@NoArgsConstructor
//注解在类上；为类提供一个全参的构造方法
@AllArgsConstructor
public class ApiResponse {

    private Integer code;

    private String message;

    private Object data;

    /** 
    * @Description: 无参构造函数 
    * @Param: [] 
    * @return:  
    * @Author: Johann 
    * @Date: 2020/9/17 
    */ 
//    private ApiResponse(){
//
//    }

    /** 
    * @Description: 全参构造函数
    * @Param: [code, message, data] 
    * @return:  
    * @Author: Johann 
    * @Date: 2020/9/17 
    */ 
//    private ApiResponse(Integer code,String message,Object data){
//        this.code = code;
//        this.message = message;
//        this.data = data;
//    }

    /** 
    * @Description: 构造一个自定义的API返回
    * @Param: [code, message, data] 
    * @return: com.johann.springbootexceptionhandler.model.ApiResponse 
    * @Author: Johann 
    * @Date: 2020/9/17 
    */ 
    public static ApiResponse of(Integer code,String message,Object data){
        return new ApiResponse(code,message,data);
    }

    /** 
    * @Description: 构造一个成功且自定义消息的API返回
    * @Param: [message] 
    * @return: com.johann.springbootexceptionhandler.model.ApiResponse 
    * @Author: Johann 
    * @Date: 2020/9/17 
    */ 
    public static ApiResponse ofSuccessMessage(String message){
        return of(Status.OK.getCode(),message,null);
    }

    /** 
    * @Description: 构造一个有状态且带数据的API返回
    * @Param: [statusCode, data] 
    * @return: com.johann.springbootexceptionhandler.model.ApiResponse 
    * @Author: Johann 
    * @Date: 2020/9/17 
    */ 
    public static ApiResponse ofStatus(Status status,Object data){
        return of(status.getCode(),status.getMessage(),data);
    }

    /** 
    * @Description: 构造一个有状态的API返回
    * @Param: [statusCode] 
    * @return: com.johann.springbootexceptionhandler.model.ApiResponse 
    * @Author: Johann 
    * @Date: 2020/9/17 
    */ 
    public static ApiResponse ofStatus(Status status){
        return ofStatus(status,null);
    }

    /** 
    * @Description: 构造一个成功且带数据的API返回
    * @Param: [data] 
    * @return: com.johann.springbootexceptionhandler.model.ApiResponse 
    * @Author: Johann 
    * @Date: 2020/9/17 
    */ 
    public static ApiResponse ofSuccess(Object data){
        return ofStatus(Status.OK,data);
    }

    /**
    * @Description: 构造一个异常且带数据的API返回
    * @Param: [t {@link BaseException}, data]
    * @return: com.johann.springbootexceptionhandler.model.ApiResponse 
    * @Author: Johann 
    * @Date: 2020/9/17 
    */ 
    public static <T extends BaseException> ApiResponse ofException(T t,Object data){
        return of(t.getCode(),t.getMessage(),data);
    }

    /**
    * @Description: 构造一个异常的API返回
    * @Param: [t {@link BaseException}]
    * @return: com.johann.springbootexceptionhandler.model.ApiResponse
    * @Author: Johann
    * @Date: 2020/9/17
    */
    public static <T extends BaseException> ApiResponse ofException(T t){
        return ofException(t,null);
    }
}
