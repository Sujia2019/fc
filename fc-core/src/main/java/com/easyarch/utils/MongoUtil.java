package com.easyarch.utils;

import com.easyarch.utils.info.MongoInfo;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.*;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

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
//        String defaultDataBaseName = "admin";
//        //address
//        ServerAddress address = new ServerAddress(mongoInfo.getHost(), mongoInfo.getPort());
//        //client
//        MongoCredential mongoCredential = MongoCredential.createCredential(
//                mongoInfo.getUsername(),
//                defaultDataBaseName,
//                mongoInfo.getUserpwd().toCharArray());
//        MongoCredential[] mongoCredentials = new MongoCredential[]{mongoCredential};
////        mongoCredentials.add(mongoCredential);
//        MongoClient client = MongoClients.create("mongodb://47.93.225.242:27017");//        //factory
//
////        //template
////        return new MongoTemplate(factory);
//        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
//        mongo.setHost("47.93.225.242");
//        mongo.setCredential(mongoCredentials);
//
//
//        SimpleMongoClientDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(mongo, defaultDataBaseName);
//        new SimpleMongoClientDatabaseFactory(MongoClients.create(), "database");


        MongoClient client = MongoClients.create(
                MongoClientSettings.builder()
                .credential(MongoCredential.createCredential("admin", "admin", "admin".toCharArray()))
                .applyToClusterSettings(settings  -> {
                    settings.hosts(singletonList(new ServerAddress("47.93.225.242", 27017)));
                }).build());
        MongoDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(client,"admin");

        return new MongoTemplate(factory);
    }

}
