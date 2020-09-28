package com.johann.springbootjdbctemplate.dao;

import com.johann.springbootjdbctemplate.dao.base.BaseDao;
import com.johann.springbootjdbctemplate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserDao
 * @Description: TODO
 * @Author: Johann
 * @Date: 2020-09-24
 **/
@Repository
public class UserDao extends BaseDao<User,Long> {

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }


    public Integer insert(User u){
        return super.insert(u,true);
    }

    public Integer delete(Long id){
        return super.deleteById(id);
    }

    public Integer update(User u,Long id){
        return super.updateById(u,id,true);
    }

    public User selectById(Long id){
        return super.findOneById(id);
    }

    public List<User> selectUserList(User u){
        return super.findByExample(u);
    }
}
