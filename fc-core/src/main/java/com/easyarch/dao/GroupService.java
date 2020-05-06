package com.easyarch.dao;

import com.easyarch.dao.mapper.GroupMsg;

public abstract class GroupService {

    /**
     *
     * @param group 创建组的信息
     * @return 是否创建成功
     */
    public abstract GroupMsg createGroup(GroupMsg group);

    //解散组
    public abstract GroupMsg destroyGroup(GroupMsg groupMsg);

    //添加管理员
    public abstract GroupMsg insertManager(String groupId, String userId);

    //退出该组
    public abstract GroupMsg quitGroup(String groupId,String userId);


}
