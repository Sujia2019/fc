package com.easyarch.serviceImp;

import com.easyarch.api.chat.ChatService;
import com.easyarch.handler.NettyServerInitializer;
import com.easyarch.model.Message;
import com.easyarch.model.chat.SendMessage;
import com.easyarch.model.chat.Type.MsgType;
import com.easyarch.model.code.CODE;
import com.easyarch.utils.TimeUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImp extends ChatService {
//    Lo

    public Object getObj(Object msg){
        SendMessage sm = (SendMessage)msg;
        System.out.println(sm.getFromId());
        short type = sm.getType();
        if(type == MsgType.ALL){
            return sendMessageToAll(sm);
        }else if(type== MsgType.GROUP){
            return sendMessageToGroup(sm);
        }else if(type== MsgType.ONE){
            return sendMessageToOne(sm);
        }else{
            return "消息类型错误";  //未来要将错误也转化成错误代数
        }
    }

    @Override
    public SendMessage sendMessageToOne(SendMessage sm) {
        String toId = sm.getToId();
        ChannelId id = NettyServerInitializer.userMap.get(toId);
        Channel channel = NettyServerInitializer.group.find(id);
        if(channel.isActive()){
            //如果在线，发送即时消息
            channel.writeAndFlush(new Message(CODE.MESSAGE,sm));
        }else{
            System.out.println(TimeUtils.getAllTime()+"---来自--"+sm.getFromId()+"--的离线消息:"+sm.getMsg());
            //发送离线留言
        }
        return null;
    }

    @Override
    public SendMessage sendMessageToGroup(SendMessage sm) {
        String groupId = sm.getToGroupId();
        ChannelGroup cg = GroupServiceImp.groupMap.get(groupId);
        if(cg!=null){
            cg.writeAndFlush(new Message(CODE.MESSAGE,sm));
            return null;
        }
        System.out.println("发送失败");
        return null;
    }

    @Override
    public SendMessage sendMessageToAll(SendMessage sm) {
        NettyServerInitializer.group.writeAndFlush(new Message(CODE.MESSAGE,sm));
        return null;
    }
}
