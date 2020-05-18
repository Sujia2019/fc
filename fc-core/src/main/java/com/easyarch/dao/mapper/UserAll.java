package com.easyarch.dao.mapper;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/*
个人文档
 */
@Data
@Document("userAll")
public class UserAll {
    private String userId;
    private String userpwd;
    private List<String> groupIds;
    private List<FightRecord> fightRecord;
    private List<String> friends;
    private UserSetting setting;

}
