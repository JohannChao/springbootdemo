package com.johann.springbootjdbctemplate.service;


import com.johann.springbootjdbctemplate.entity.User;

import java.util.List;

/**
* @Description: service接口
* @Param:  
* @return:  
* @Author: Johann 
* @Date: 2020/9/24 
*/ 
public interface UserService {

    /** 
    * @Description: 保存新的用户
    * @Param: [u] 
    * @return: java.lang.Boolean  保存成功 {@code true} 保存失败 {@code false}
    * @Author: Johann 
    * @Date: 2020/9/24 
    */ 
    Boolean save(User u);

    /**
    * @Description: 根据主键，删除用户
    * @Param: [id] 
    * @return: java.lang.Boolean 删除成功 {@code true} 删除失败 {@code false}
    * @Author: Johann
    * @Date: 2020/9/24
    */
    Boolean delete(Long id);

    /** 
    * @Description: 根据主键ID，更新用户
    * @Param: [u, id] 
    * @return: java.lang.Boolean 更新成功 {@code true} 更新失败 {@code false}
    * @Author: Johann 
    * @Date: 2020/9/24 
    */ 
    Boolean update(User u,Long id);

    /** 
    * @Description: 根据主键ID，查询单个用户
    * @Param: [id] 
    * @return: com.johann.springbootjdbctemplate.entity.User 单个用户
    * @Author: Johann 
    * @Date: 2020/9/24 
    */ 
    User getUser(Long id);

    /** 
    * @Description: 根据条件获取用户列表
    * @Param: [u] 查询条件
    * @return: java.util.List<com.johann.springbootjdbctemplate.entity.User> 用户列表
    * @Author: Johann 
    * @Date: 2020/9/24 
    */ 
    List<User> getUser(User u);
}
