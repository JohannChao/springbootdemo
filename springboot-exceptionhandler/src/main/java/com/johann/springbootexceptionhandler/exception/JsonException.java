package com.johann.springbootexceptionhandler.exception;

import com.johann.springbootexceptionhandler.constant.Status;
import lombok.Getter;

/**
 * @ClassName: JsonException
 * @Description: Json 异常
 * @Author: Johann
 * @Date: 2020-09-17 16:46
 **/
@Getter
public class JsonException extends BaseException {

    public JsonException(Integer code,String message){
        super(code, message);
    }

    public JsonException(Status status) {
        super(status);
    }
}
