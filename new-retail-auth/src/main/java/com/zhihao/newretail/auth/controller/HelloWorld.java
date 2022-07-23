package com.zhihao.newretail.auth.controller;

import com.zhihao.newretail.core.exception.ServiceException;
import com.zhihao.newretail.core.util.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/*
* 留个彩蛋，时隔一年重新启程！
* */
@RefreshScope
@RestController
public class HelloWorld {

    @Value("${my.name}")
    private String name;

    @Value("${my.age}")
    private Integer age;

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/name")
    public R name() {
        Map<String, Object> HashMap = new HashMap<>();
        HashMap.put("name", name);
        HashMap.put("age", age);
        return R.ok().put("data", HashMap);
    }

}
