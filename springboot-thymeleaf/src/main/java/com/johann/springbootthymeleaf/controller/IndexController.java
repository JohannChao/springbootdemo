package com.johann.springbootthymeleaf.controller;

import cn.hutool.core.util.ObjectUtil;
import com.johann.springbootthymeleaf.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: IndexController
 * @Description: index首页
 * @Author: Johann
 * @Date: 2020-09-18 17:42
 **/
@Controller
public class IndexController {

    @GetMapping(value = {"","/"})
    public ModelAndView index(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();

        User user = (User) request.getSession().getAttribute("user");
        if(ObjectUtil.isNull(user)){
            modelAndView.setViewName("redirect:/user/login");
        }else{
            modelAndView.setViewName("page/index");
            modelAndView.addObject(user);
        }
        return modelAndView;
    }
}
