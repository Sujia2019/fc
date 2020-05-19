package com.easyarch.dao.mapper;

import com.easyarch.utils.MongoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class UserAllDoc {
    @Autowired
    private MongoTemplate mongoTemplate;

//    private MongoUtil mongoUtil = new MongoUtil();

    /*
    插入用户记录
     */
    public void insertUserDoc(UserAll userAll){
        mongoTemplate.insert(userAll);
    }
    /*
    更新用户文档
     */
    public void updateUserDoc(UserAll userAll){

    }
    /*
    更新用户部分文档
     */
    public void updateUserPartDoc(){

    }

    /*
    删除用户文档
     */
    public void delUserDoc(String userId){
        Query query=new Query(Criteria.where("userId").is(userId));
        mongoTemplate.remove(query,GroupMsg.class,"userAll").getDeletedCount();
    }

    public UserAll getUserDoc(String userId){
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, UserAll.class);
    }
}
