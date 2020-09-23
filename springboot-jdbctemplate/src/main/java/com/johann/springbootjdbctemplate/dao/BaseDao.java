package com.johann.springbootjdbctemplate.dao;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.johann.springbootjdbctemplate.annotation.Column;
import com.johann.springbootjdbctemplate.annotation.Ignore;
import com.johann.springbootjdbctemplate.annotation.Pk;
import com.johann.springbootjdbctemplate.annotation.Table;
import com.johann.springbootjdbctemplate.constant.ConstantPool;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName: BaseDao
 * @Description: Dao 基类
 * @Author: Johann
 * @Date: 2020-09-23
 **/

/**
*  需要了解复习的
 *
 *  java 泛型
 *
 *  lambda表达式
 *
 *  java反射
 *
 *  函数式编程
*/

@Slf4j
public class BaseDao<T,P> {

    private JdbcTemplate jdbcTemplate;
    private Class<T> clazz;

    public BaseDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        clazz = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /** 
    * @Description: 通用插入，自增列需要添加 {@link Pk} 注解
    * @Param: [t, ignoreNull] 对象, 是否忽略 null 值
    * @return: java.lang.Integer 操作的行数
    * @Author: Johann 
    * @Date: 2020/9/23 
    */ 
    protected Integer insert(T t,Boolean ignoreNull){
        String table = getTableName(t);

        List<Field> filterField = getField(t,ignoreNull);

        List<String> columnList = getColumns(filterField);

        // 逗号拼接列名称
        String columns = StrUtil.join(ConstantPool.SEPARATOR_COMMA,columnList);

        // 构造占位符
        String params = StrUtil.repeatAndJoin("?",columnList.size(),ConstantPool.SEPARATOR_COMMA);

        // 构造值
        Object[] values = filterField.stream().map(field -> ReflectUtil.getFieldValue(t,field)).toArray();

        String sql = StrUtil.format("INSERT INTO {table} ({columns}) Values ({params})", Dict.create().set("table",table).set("columns",columns).set("params",params));

        log.debug("【执行sql】 ：{} ",sql);
        log.debug("【执行sql】 参数： {}", JSONUtil.toJsonStr(values));
        return jdbcTemplate.update(sql, values);
    }

    /** 
    * @Description: 获取表名
    * @Param: [t] 对象
    * @return: java.lang.String 表名称
    * @Author: Johann 
    * @Date: 2020/9/23 
    */ 
    private String getTableName(T t){
        Table tableAnnotation =  t.getClass().getAnnotation(Table.class);
        if(ObjectUtil.isNotNull(tableAnnotation)){
            return StrUtil.format("`{}`",tableAnnotation.name());
        }else{
            return  StrUtil.format("`{}`",t.getClass().getName().toLowerCase());
        }
    }

    /** 
    * @Description: 获取表名称
    * @Param: [] 
    * @return: java.lang.String 
    * @Author: Johann 
    * @Date: 2020/9/23 
    */ 
    private String getTableName(){
        Table tableAnnotation =  clazz.getAnnotation(Table.class);
        if(ObjectUtil.isNotNull(tableAnnotation)){
            return StrUtil.format("`{}`",tableAnnotation.name());
        }else{
            return  StrUtil.format("`{}`",clazz.getName().toLowerCase());
        }
    }


    /** 
    * @Description: 获取列名称
    * @Param: [fieldList] 字段列表
    * @return: java.util.List<java.lang.String> 列消息
    * @Author: Johann 
    * @Date: 2020/9/23 
    */ 
    private List<String> getColumns(List<Field> fieldList){

        List<String> columnList = CollUtil.newArrayList();

        for (Field field: fieldList) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            String columnName;
            if(ObjectUtil.isNotNull(columnAnnotation)){
                columnName = columnAnnotation.name();
            }else{
                columnName = field.getName();
            }
            columnList.add(StrUtil.format("`{}`",columnName));
        }
        return columnList;
    }

    /** 
    * @Description: 获取字段列表 {@code 过滤数据库中不存在的字段，以及自增列} 
    * @Param: [t, ignoreNull] 对象，是否忽略空值
    * @return: java.util.List<java.lang.reflect.Field> 
    * @Author: Johann 
    * @Date: 2020/9/23 
    */ 
    private List<Field> getField(T t, Boolean ignoreNull){
        // 获取所有字段，包含父类中的字段
        Field[] files = ReflectUtil.getFields(t.getClass());

        // 过滤数据库中不存在的字段，以及自增列
        List<Field> filterField;
        Stream<Field> fieldStream = CollUtil.toList(files).stream().filter(
                field -> ObjectUtil.isNull(field.getAnnotation(Ignore.class))||ObjectUtil.isNull(field.getAnnotation(Pk.class))
        );

        // 是否过滤字段值为null的字段
        if(ignoreNull){
            filterField = fieldStream.filter(field -> ObjectUtil.isNotNull(ReflectUtil.getFieldValue(t,field))).collect(Collectors.toList());
        }else{
            filterField = fieldStream.collect(Collectors.toList());
        }
        return filterField;
    }

}
