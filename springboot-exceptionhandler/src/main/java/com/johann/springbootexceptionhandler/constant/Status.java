package com.johann.springbootexceptionhandler.constant;

import lombok.Getter;

/**
 * @ClassName: StatusCode
 * @Description: 状态码
 * @Author: Johann
 * @Date: 2020-09-17 16:13
 **/
@Getter
public enum Status {

    /**
     * 操作成功
     */
    OK(200, "操作成功"),

    /**
     * 未知异常
     */
    UNKNOWN_ERROR(500, "服务器出错啦");

    /**
     * 未知异常
     */


    /**
    * @Description: 状态码 
    * @Param:  
    * @return:  
    * @Author: Johann 
    * @Date: 2020/9/17 
    */ 
    private Integer code;

    /** 
    * @Description: 异常描述
    * @Param:  
    * @return:  
    * @Author: Johann 
    * @Date: 2020/9/17 
    */ 
    private String message;

    /**
    * @Description:  异常状态码及其内容
    * @Param: [code, message]
    * @return:
    * @Author: Johann
    * @Date: 2020/9/17
    */
    Status(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
