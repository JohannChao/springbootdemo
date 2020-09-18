package com.johann.springbootexceptionhandler.handler;

import com.johann.springbootexceptionhandler.exception.JsonException;
import com.johann.springbootexceptionhandler.exception.PageException;
import com.johann.springbootexceptionhandler.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: DemoExceptionHandler
 * @Description: 统一异常处理
 * @Author: Johann
 * @Date: 2020-09-17 17:01
 **/
@Slf4j
@ControllerAdvice
public class DemoExceptionHandler {
    private static final String DEFAULT_ERROR_VIEW = "error";

        /**
        * @Description: 统一 Json 异常处理
        * @Param: [e]
        * @return: com.johann.springbootexceptionhandler.model.ApiResponse
         *  统一返回 json 格式
        * @Author: Johann
        * @Date: 2020/9/17
        */
        @ExceptionHandler(value = JsonException.class)
        @ResponseBody
      public ApiResponse jsonErrorHandler(JsonException e){
          log.error("【JsonException】：{}",e.getMessage());
          return ApiResponse.ofException(e);
      }


      /** 
      * @Description:  统一 页面 异常处理
      * @Param: [e] 
      * @return: org.springframework.web.servlet.ModelAndView
       *  统一跳转到异常页面
      * @Author: Johann 
      * @Date: 2020/9/17 
      */ 
      @ExceptionHandler(value = PageException.class)
      public ModelAndView pageErrorHandler(PageException e){
            log.error("【PageException】：{}",e.getMessage());
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("message",e.getMessage());
            modelAndView.setViewName(DEFAULT_ERROR_VIEW);
            return modelAndView;
      }
}
