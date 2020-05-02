package com.easyarch.api.chat;

import com.easyarch.model.chat.GroupMsg;

public abstract class GroupService {

    /**
     *
     * @param group 创建组的信息
     * @return 是否创建成功
     */
    public abstract boolean createGroup(GroupMsg group);

    //解散组
    public abstract boolean destroyGroup(GroupMsg groupMsg);

    //添加管理员
    public abstract boolean addManager(String groupId, String userId);


    //退出该组
    public abstract boolean quitGroup();


}
