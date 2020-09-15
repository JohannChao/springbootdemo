package com.johann.springbootproperties.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ApplicationProperty 读取项目配置文件
 * @Description: TODO
 * @Author: Johann
 * @Date: 2020-09-15 11:21
 **/
@Data
@Component
public class ApplicationProperty {

    @Value("${application.name}")
    private String name;

    @Value("${application.version}")
    private String version;
}
