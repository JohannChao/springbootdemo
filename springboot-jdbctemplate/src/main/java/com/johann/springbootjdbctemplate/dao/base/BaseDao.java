package com.johann.springbootjdbctemplate.dao.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.johann.springbootjdbctemplate.annotation.Column;
import com.johann.springbootjdbctemplate.annotation.Ignore;
import com.johann.springbootjdbctemplate.annotation.Pk;
import com.johann.springbootjdbctemplate.annotation.Table;
import com.johann.springbootjdbctemplate.constant.ConstantPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
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
    * @Description: 通用根据主键删除
    * @Param: [pk] 
    * @return: java.lang.Integer 
    * @Author: Johann 
    * @Date: 2020/9/24 
    */ 
    protected Integer deleteById(P pk){
        String tableName = getTableName();

        String sql = StrUtil.format("DELETE FROM {table} WHERE ID = ?",Dict.create().set("table",tableName));

        log.debug("【执行SQL】 SQL ：{}",sql);
        log.debug("【执行SQL】 参数 ：{}",JSONUtil.toJsonStr(pk));
        return jdbcTemplate.update(sql,pk);
    }

    /** 
    * @Description: 通用根据主键更新，自增列需要添加 {@link Pk} 注解
    * @Param: [t, pk, ignoreNull]  对象  主键
    * @return: java.lang.Integer 
    * @Author: Johann 
    * @Date: 2020/9/24 
    */ 
    protected Integer updateById(T t,P pk,Boolean ignoreNull){
        String tableName = getTableName(t);

        List<Field> filterField = getField(t,ignoreNull);

        List<String> columnList = getColumns(filterField);

        List<String> columns = columnList.stream().map(s -> StrUtil.appendIfMissing(s," = ?")).collect(Collectors.toList());
        String params = StrUtil.join(ConstantPool.SEPARATOR_COMMA,columns);

        // 构造值
        List<Object> valueList = filterField.stream().map(field -> ReflectUtil.getFieldValue(t,field)).collect(Collectors.toList());
        valueList.add(pk);

        Object[] values = ArrayUtil.toArray(valueList,Object.class);

        String sql = StrUtil.format("UPDATE {table} SET {params} WHERE ID = ?",Dict.create().set("table",tableName).set("params",params));

        log.debug("【执行SQL】 SQL ：{}",sql);
        log.debug("【执行SQL】 参数 ：{}",JSONUtil.toJsonStr(values));

        return jdbcTemplate.update(sql,values);
    }

    /** 
    * @Description:  根据主键查询单条记录
    * @Param: [pk]  主键
    * @return: T 单条记录
    * @Author: Johann 
    * @Date: 2020/9/24 
    */ 
    public T findOneById(P pk) {
        String tableName = getTableName();
        String sql = StrUtil.format("SELECT * FROM {table} where id = ?", Dict.create().set("table", tableName));
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(clazz);
        log.debug("【执行SQL】SQL：{}", sql);
        String jsonstr = JSONUtil.toJsonStr(pk);
        log.debug("【执行SQL】参数：{}", JSONUtil.toJsonStr(pk));
        return jdbcTemplate.queryForObject(sql, new Object[]{pk}, rowMapper);
    }

    /** 
    * @Description: 根据对象查询符合条件的记录
    * @Param: [t] 查询条件
    * @return: java.util.List<T> 对象列表
    * @Author: Johann 
    * @Date: 2020/9/24 
    */ 
    public List<T> findByExample(T t) {
        String tableName = getTableName(t);
        List<Field> filterField = getField(t, true);
        List<String> columnList = getColumns(filterField);

        List<String> columns = columnList.stream().map(s -> " and " + s + " = ? ").collect(Collectors.toList());

        String where = StrUtil.join(" ", columns);
        // 构造值
        Object[] values = filterField.stream().map(field -> ReflectUtil.getFieldValue(t, field)).toArray();

        String sql = StrUtil.format("SELECT * FROM {table} where 1=1 {where}", Dict.create().set("table", tableName).set("where", StrUtil.isBlank(where) ? "" : where));
        log.debug("【执行SQL】SQL：{}", sql);
        log.debug("【执行SQL】参数：{}", JSONUtil.toJsonStr(values));
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, values);
        List<T> ret = CollUtil.newArrayList();
        maps.forEach(map -> ret.add(BeanUtil.fillBeanWithMap(map, ReflectUtil.newInstance(clazz), true, false)));
        return ret;
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
