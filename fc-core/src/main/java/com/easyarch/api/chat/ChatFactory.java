package com.easyarch.api.chat;

import com.easyarch.api.MatchFactory;
import com.easyarch.api.MessageFactory;
import com.easyarch.cache.Maps;
import com.easyarch.model.Message;
import com.easyarch.model.chat.SendMessage;
import com.easyarch.model.chat.Type.MsgType;
import com.easyarch.model.code.CODE;
import com.easyarch.utils.TimeUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChatFactory extends ChatService implements MessageFactory {
    private static final Logger logger = LoggerFactory.getLogger(ChatFactory.class);


    @Override
    public Message handle(Message msg) {
        SendMessage sm = (SendMessage)msg.getObj();
        System.out.println(sm.getFromId());
        short type = sm.getType();
        if(type == MsgType.ALL){
            msg.setObj(sendMessageToAll(sm));
        }else if(type== MsgType.GROUP){
            msg.setObj(sendMessageToGroup(sm));
        }else if(type== MsgType.ONE){
            msg.setObj(sendMessageToOne(sm));
        }else{
            msg.setObj(sendError(sm));
        }
        return msg;
    }

    @Override
    public SendMessage sendMessageToOne(SendMessage sm) {
        String toId = sm.getToId();
//        ChannelId id = null;
        Channel channel = null;
        if(isInvalid(toId)){
            ChannelId id = Maps.userMap.get(toId);
            channel = Maps.group.find(id);
        }else{
            return sendError(sm);
        }
        if(channel!=null){
            if(channel.isActive()){
                //如果在线，发送即时消息
                channel.writeAndFlush(new Message(CODE.MESSAGE,sm));
            }
        }
        else{
            System.out.println(TimeUtils.getAllTime()+"---来自--"+sm.getFromId()+"--的离线消息:"+sm.getMsg());
            //发送离线留言
        }
        return sendSuccess(sm);
    }

    @Override
    public SendMessage sendMessageToGroup(SendMessage sm) {
        String groupId = sm.getToGroupId();
        ChannelGroup cg = Maps.groupMap.get(groupId);
        if(cg!=null){
            cg.writeAndFlush(new Message(CODE.MESSAGE,sm));
            return sendSuccess(sm);
        }
//        System.out.println("发送失败");
        return sendError(sm);
    }

    @Override
    public SendMessage sendMessageToAll(SendMessage sm) {
        Maps.group.writeAndFlush(new Message(CODE.MESSAGE,sm));
        return sendSuccess(sm);
    }

    private SendMessage sendSuccess(SendMessage sm){
        sm.setType(MsgType.SUCCESS);
        sm.setMsg("发送成功");
        return sm;
    }

    private SendMessage sendError(SendMessage sm){
        sm.setMsg("发送失败");
        sm.setType(MsgType.ERROR);
        return sm;
    }

    private boolean isInvalid(String toId){
        if (!Maps.userMap.containsKey(toId)){
            return false;
        }
        if(null==Maps.userMap.get(toId)){
            return false;
        }
        return true;
    }
}
