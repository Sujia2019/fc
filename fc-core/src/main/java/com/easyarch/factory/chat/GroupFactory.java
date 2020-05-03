package com.easyarch.factory.chat;

import com.easyarch.api.MessageFactory;
import com.easyarch.api.chat.GroupService;
import com.easyarch.cache.Maps;
import com.easyarch.dao.mapper.GroupMapper;
import com.easyarch.model.Message;
import com.easyarch.model.chat.GroupMsg;
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
    private GroupMapper mapper;

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
            return null;
        }
    }

    @Override
    public GroupMsg destroyGroup(GroupMsg group) {
        Maps.groupMap.remove(group.getGroupId());
        return null;
    }

    @Override
    public GroupMsg addManager(String groupId, String userId) {

        return null;
    }

    @Override
    public GroupMsg quitGroup() {

        return null;
    }
}
