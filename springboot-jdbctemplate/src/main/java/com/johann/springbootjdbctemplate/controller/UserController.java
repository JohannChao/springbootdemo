package com.johann.springbootjdbctemplate.controller;

import cn.hutool.core.lang.Dict;
import com.johann.springbootjdbctemplate.entity.User;
import com.johann.springbootjdbctemplate.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: UserController
 * @Description: TODO
 * @Author: Johann
 * @Date: 2020-09-24
 **/
@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public Dict save(@RequestBody User u){
        Boolean save = userService.save(u);
        return Dict.create().set("code",save ? 200 : 500).set("msg",save ? "成功" : "失败").set("data",save ? u : null);
    }

    @DeleteMapping("user/{id}")
    public Dict delete(@PathVariable Long id){
        Boolean delete = userService.delete(id);
        return Dict.create().set("code",delete ? 200 : 500).set("msg",delete ? "成功" : "失败");
    }

    @PutMapping("user/{id}")
    public Dict update(@RequestBody User u,@PathVariable Long id){
        Boolean update = userService.update(u,id);
        return Dict.create().set("code",update ? 200 : 500).set("msg",update ? "成功" : "失败").set("data",update ? u : null);
    }

    @GetMapping("user/{id}")
    public Dict getUser(@PathVariable Long id){
        User u = userService.getUser(id);
        return Dict.create().set("code", 200).set("msg", "成功").set("data", u);
    }

    @GetMapping("/user")
    public Dict getUser(@RequestBody User u){
        List<User> userList = userService.getUser(u);
        return Dict.create().set("code", 200).set("msg", "成功").set("data", userList);
    }
}
