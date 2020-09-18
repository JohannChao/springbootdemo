package com.johann.springbootexceptionhandler.exception;

import com.johann.springbootexceptionhandler.constant.Status;

/**
 * @ClassName: PageException
 * @Description: 页面异常
 * @Author: Johann
 * @Date: 2020-09-17 16:53
 **/
public class PageException extends BaseException{

    public PageException(Status status){
        super(status);
    }

    public PageException(Integer code,String message){
        super(code,message);
    }
}
