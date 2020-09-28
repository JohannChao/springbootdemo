package com.johann.springbootjdbctemplate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.johann.springbootjdbctemplate.constant.ConstantPool;
import com.johann.springbootjdbctemplate.dao.UserDao;
import com.johann.springbootjdbctemplate.entity.User;
import com.johann.springbootjdbctemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @Author: Johann
 * @Date: 2020-09-24 13:56
 **/
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /** 
    * @Description:  
    * @Param: [u] 
    * @return: java.lang.Boolean 
    * @Author: Johann 
    * @Date: 2020/9/24 
    */ 
    @Override
    public Boolean save(User u) {
        String pwd = u.getPassword();
        String salt = IdUtil.simpleUUID();
        String password = SecureUtil.md5(pwd + ConstantPool.SALT_PREFIX + salt);
        u.setSalt(salt);
        u.setPassword(password);
        return userDao.insert(u) > 0;
    }

    /** 
    * @Description:  
    * @Param: [id] 
    * @return: java.lang.Boolean 
    * @Author: Johann 
    * @Date: 2020/9/24 
    */ 
    @Override
    public Boolean delete(Long id) {
        return userDao.delete(id) > 0;
    }

    /** 
    * @Description:  
    * @Param: [u, id] 
    * @return: java.lang.Boolean 
    * @Author: Johann 
    * @Date: 2020/9/24 
    */ 
    @Override
    public Boolean update(User u, Long id) {

        User exist = getUser(id);

        if(StrUtil.isNotBlank(u.getPassword())){
            String pwd = u.getPassword();
            String salt = IdUtil.simpleUUID();
            String password = SecureUtil.md5(pwd + ConstantPool.SALT_PREFIX + salt);
            u.setSalt(salt);
            u.setPassword(password);
        }
        BeanUtil.copyProperties(u,exist, CopyOptions.create().setIgnoreNullValue(true));
        exist.setLastUpdateTime(new DateTime());
        return userDao.update(exist,id) > 0;
    }

    /** 
    * @Description:
    * @Param: [id] 
    * @return: com.johann.springbootjdbctemplate.entity.User 
    * @Author: Johann 
    * @Date: 2020/9/24 
    */ 
    @Override
    public User getUser(Long id) {
        return userDao.findOneById(id);
    }

    /** 
    * @Description:
    * @Param: [u] 
    * @return: java.util.List<com.johann.springbootjdbctemplate.entity.User> 
    * @Author: Johann 
    * @Date: 2020/9/24 
    */ 
    @Override
    public List<User> getUser(User u) {
        return userDao.findByExample(u);
    }
}
