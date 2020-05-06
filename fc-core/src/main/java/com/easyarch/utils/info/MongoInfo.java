package com.easyarch.utils.info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:config.properties")
public class MongoInfo {

    @Value("${server.ip}")
    private String host;
    @Value("${mongo.username}")
    private String username;
    @Value("${mongo.userpwd}")
    private String userpwd;
    @Value("#{${mongo.port}}")
    private int port;


    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public int getPort() {
        return port;
    }
}
