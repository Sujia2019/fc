package com.easyarch.dao;

import com.easyarch.model.chat.GroupMsg;

public interface GroupDao {

    int createGroup(GroupMsg group);

    //添加管理员
    int addGroupManager(String groupId,String userId);

    int deleteGroup(String groupId);


}
