package com.easyarch.utils.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.easyarch.*")
@MapperScan("com.easyarch.dao.mapper")
public class NettyConfig {

}
