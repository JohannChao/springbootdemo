package com.johann.springbootexceptionhandler.controller;

import com.johann.springbootexceptionhandler.constant.Status;
import com.johann.springbootexceptionhandler.exception.JsonException;
import com.johann.springbootexceptionhandler.exception.PageException;
import com.johann.springbootexceptionhandler.model.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: TestController
 * @Description: TODO
 * @Author: Johann
 * @Date: 2020-09-17 17:24
 **/
@Controller
public class TestController {

    @ResponseBody
    @GetMapping("/json")
    public ApiResponse jsonException(){
        throw new JsonException(Status.UNKNOWN_ERROR);
    }

    @GetMapping("/page")
    public ModelAndView pageException(){
        throw new PageException(Status.UNKNOWN_ERROR);
    }

}
