package com.johann.springbootdemohelloworld;

import cn.hutool.core.util.StrUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootdemoHelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootdemoHelloworldApplication.class, args);
    }

    /** 
    * @Description:  http://localhost:8080/demo/hello?name=China
     * http://localhost:8080/demo/hello
    * @Param: [name] 
    * @return: java.lang.String 
    * @Author: Johann 
    * @Date: 2020/9/15 
    */ 
    @GetMapping("/hello")
    public String hello(@RequestParam(required = false,name = "name") String name){
        if(StrUtil.isBlank(name)){
            name = "Johann";
        }
        return StrUtil.format("Hello,{}!",name);
    }
}
