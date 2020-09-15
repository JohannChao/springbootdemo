package com.johann.springbootproperties.controller;

import cn.hutool.core.lang.Dict;
import com.johann.springbootproperties.property.ApplicationProperty;
import com.johann.springbootproperties.property.DevloperProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: PropertyController
 * @Description: TODO
 * @Author: Johann
 * @Date: 2020-09-15 11:31
 **/
@RestController
public class PropertyController {

    private ApplicationProperty applicationProperty;
    private DevloperProperty devloperProperty;

    @Autowired
    public PropertyController(ApplicationProperty applicationProperty,DevloperProperty devloperProperty){
        this.applicationProperty = applicationProperty;
        this.devloperProperty = devloperProperty;
    }

    @GetMapping("/property")
    public Dict getDict(){
        return Dict.create().set("applicationProperty",applicationProperty).set("devloperProperty",devloperProperty);
    }
}
