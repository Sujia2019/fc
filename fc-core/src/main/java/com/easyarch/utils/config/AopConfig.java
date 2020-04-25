package com.easyarch.utils.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.easyarch.cache")
@EnableAspectJAutoProxy
public class AopConfig {

}
