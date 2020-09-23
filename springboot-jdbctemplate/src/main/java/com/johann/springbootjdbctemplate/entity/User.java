package com.johann.springbootjdbctemplate.entity;

import com.johann.springbootjdbctemplate.annotation.Column;
import com.johann.springbootjdbctemplate.annotation.Pk;
import com.johann.springbootjdbctemplate.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: User
 * @Description: TODO
 * @Author: Johann
 * @Date: 2020-09-21 17:44
 **/
@Data
@Table(name = "orm_user")
public class User implements Serializable {

    /**
    * @Description:  主键
    * @Param:
    * @return:
    * @Author: Johann
    * @Date: 2020/9/21
    */
    @Pk
    private Long id;

    private String name;//用户名

    private String password;//加密后的密码

    private String salt;//加密使用的盐值

    private String email;//邮箱

    @Column(name ="phone_number")
    private String phonewNumber;//手机号码

    private Integer status;//逻辑删除 -1：逻辑删除， 0：禁用 ，1：启用

    @Column(name = "create_time")
    private Date createTime;//创建时间

    @Column(name = "last_login_time")
    private Date lastLoginTime;//上次登陆时间

    @Column(name = "last_update_time")
    private Date lastUpdateTime;//上次更新时间

}
