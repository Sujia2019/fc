package com.easyarch.utils;

import com.easyarch.dao.mapper.FightRecord;
import com.easyarch.dao.mapper.UserAll;
import com.easyarch.dao.mapper.UserAllDoc;
import com.easyarch.dao.mapper.UserSetting;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        MongoUtil util = new MongoUtil();
//        UserAllDoc doc = new UserAllDoc();
//
        UserAll userDoc = new UserAll();
        userDoc.setUserId("18539403150");
        userDoc.setUserpwd("123456");
        ArrayList<String> groups = new ArrayList<>();
        groups.add("111");
        userDoc.setGroupIds(groups);
        ArrayList<String> friends = new ArrayList<>();
        userDoc.setFriends(friends);
        ArrayList<FightRecord> records = new ArrayList<>();
        userDoc.setFightRecord(records);
        UserSetting setting = new UserSetting();
        setting.setAcceptFriendRequest(true);
        setting.setAcceptMessage(true);
        userDoc.setSetting(setting);
//
//        doc.insertUserDoc(userDoc);
        MongoOperations mongoOps = util.getTemplate();

        mongoOps.insert(userDoc);
//        util.getMongoTemplate().insert(userDoc);


    }
}
