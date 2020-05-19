package com.easyarch.api.chat;

import com.easyarch.api.MessageFactory;
import com.easyarch.cache.Maps;
import com.easyarch.dao.mapper.GroupMsg;
import com.easyarch.dao.GroupService;
import com.easyarch.dao.mapper.GroupDao;
import com.easyarch.model.Message;
import com.easyarch.model.chat.Type.GroupStatus;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupFactory extends GroupService implements MessageFactory{

    @Autowired
    private GroupDao dao;

    @Override
    public Message handle(Message msg){
        GroupMsg group = (GroupMsg)msg.getObj();
        short status = group.getStatus();
        if(status == GroupStatus.APPLY){
            msg.setObj(createGroup(group));
        }
        if(status == GroupStatus.DELETE){
            msg.setObj(destroyGroup(group));
        }
        return msg;
    }
    @Override
    public GroupMsg createGroup(GroupMsg group) {
        String groupId = group.getGroupId();
        List<String> members = group.getMembersId();
        ChannelGroup cg = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        for (String member : members) {
            ChannelId id = Maps.userMap.get(member);
            Channel channel = Maps.group.find(id);
            cg.add(channel);
        }
        if(cg.isEmpty()){
            return null;
        }else{
            Maps.groupMap.put(groupId,cg);
        }
        //入库一份
        dao.createGroup(group);
        return group;
    }

    /*
    彻底删除组
     */
    @Override
    public GroupMsg destroyGroup(GroupMsg group) {
        Maps.groupMap.remove(group.getGroupId());
        dao.deleteGroup(group.getGroupId());
        return null;
    }

    /*
    添加管理员
     */
    @Override
    public GroupMsg insertManager(String groupId, String userId) {
        dao.insertManager(groupId,userId);
        return dao.searchGroup(groupId);
    }

    /*
    退出该组
     */
    @Override
    public GroupMsg quitGroup(String groupId, String userId) {
        ChannelId channelId = Maps.userMap.get(userId);

        Channel future = Maps.groupMap.get(groupId).find(channelId);
        Maps.groupMap.get(groupId).remove(future);

        dao.delMember(groupId,userId);
        return null;
    }

}
