package com.johann.springbootthymeleaf.controller;

import com.johann.springbootthymeleaf.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: UserController
 * @Description: TODO
 * @Author: Johann
 * @Date: 2020-09-18 17:48
 **/
@Controller
@RequestMapping("user")
@Slf4j
public class UserController {

    @PostMapping("/logins")
    public ModelAndView login(User user, HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject(user);
        modelAndView.setViewName("redirect:/");
        //modelAndView.setViewName("page/index");

        request.getSession().setAttribute("user",user);
        return modelAndView;
    }

    @GetMapping("/logins")
    public ModelAndView login(){
        return new ModelAndView("page/login");
    }
}
