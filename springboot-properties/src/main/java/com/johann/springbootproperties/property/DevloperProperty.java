package com.johann.springbootproperties.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: DevloperProperty 读取用户自定义配置
 * @Description: TODO
 * @Author: Johann
 * @Date: 2020-09-15 11:26
 **/
@Data
@ConfigurationProperties(prefix = "developer.user2")
@Component
public class DevloperProperty {

    private String name;
    private String age;
    private String weixin;
    private String phoneNumber;
}
