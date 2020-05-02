package com.easyarch.factory;

import com.easyarch.api.MessageFactory;
import com.easyarch.api.chat.ChatService;
import com.easyarch.model.Message;
import com.easyarch.model.chat.SendMessage;
import com.easyarch.model.chat.Type.MsgType;
import com.easyarch.model.code.CODE;

public class ChatFactory extends ChatService implements MessageFactory {
    @Override
    public Message handle(Message msg) {
        SendMessage sm = (SendMessage)msg.getObj();
        System.out.println(sm.getFromId());
        short type = sm.getType();
        if(type == MsgType.ALL){
            return sendMessageToAll(sm);

        }else if(type== MsgType.GROUP){
            return sendMessageToGroup(sm);
        }else if(type== MsgType.ONE){
            return sendMessageToOne(sm);
        }
        return null;
    }

    @Override
    public Message sendMessageToOne(SendMessage sm) {

        return null;
    }

    @Override
    public Message sendMessageToGroup(SendMessage sm) {
        return null;
    }

    @Override
    public Message sendMessageToAll(SendMessage sm) {

        return null;
    }
}
