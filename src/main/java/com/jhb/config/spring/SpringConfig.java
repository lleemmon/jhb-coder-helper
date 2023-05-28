package com.jhb.config.spring;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

//  datasource:
//          type: com.alibaba.druid.pool.DruidDataSource
//          driver-class-name: com.mysql.cj.jdbc.Driver
//        url: jdbc:mysql://localhost:3306/jhb_code_helper?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8
//        username: root
//        password: 123456
@Getter
@Configuration
public class SpringConfig {
    @Value("${spring.datasource.url}")
    public String datasourceUrl;
    @Value("${spring.datasource.driver-class-name}")
    public String datasourceDriverClassName;
    @Value("${spring.datasource.username}")
    public String datasourceUsername;
    @Value("${spring.datasource.password}")
    public String datasourcePassword;
    @Value("${web-project.path}")
    public String webProjectPath;

}
