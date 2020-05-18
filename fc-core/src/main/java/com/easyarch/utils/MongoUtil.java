package com.easyarch.utils;

import com.easyarch.utils.info.MongoInfo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoUtil {
    private MongoTemplate mongoTemplate;
    @Resource
    private MongoInfo mongoInfo;

    public MongoUtil(){

    }

    public MongoTemplate getMongoTemplate(){
        if(mongoTemplate==null){
            synchronized (MongoUtil.class){
                if(mongoTemplate==null){
                    mongoTemplate=getTemplate();
                }
            }
        }
        return mongoTemplate;
    }

    public MongoTemplate getTemplate(){
        String defaultDataBaseName = "admin";
        //address
        ServerAddress address = new ServerAddress(mongoInfo.getHost(), mongoInfo.getPort());
        //client
        MongoCredential mongoCredential = MongoCredential.createCredential(
                mongoInfo.getUsername(),
                defaultDataBaseName,
                mongoInfo.getUserpwd().toCharArray());
        List<MongoCredential> mongoCredentials = new ArrayList<>();
        mongoCredentials.add(mongoCredential);
        MongoClient client = new MongoClient(address,mongoCredentials);
        //factory
        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(client, defaultDataBaseName);
        //template
        return new MongoTemplate(factory);
    }

}
