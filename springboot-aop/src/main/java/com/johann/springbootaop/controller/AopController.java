package com.johann.springbootaop.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: AopController
 * @Description: TODO
 * @Author: Johann
 * @Date: 2020-09-16 17:03
 **/
@RestController
public class AopController {

    @GetMapping("/testAop")
    public Dict testAop(String name){
        return Dict.create().set("name", StrUtil.isBlank(name) ? "Johann":name);
    }
}
