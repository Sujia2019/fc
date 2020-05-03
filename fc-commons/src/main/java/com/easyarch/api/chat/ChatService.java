package com.easyarch.api.chat;

import com.easyarch.model.Message;
import com.easyarch.model.chat.SendMessage;

public abstract class ChatService {

    /**
     *
     * @param sm 发送的消息类型
     * @return 给客户端反馈
     */
    public abstract SendMessage sendMessageToOne(SendMessage sm);

    public abstract SendMessage sendMessageToGroup(SendMessage sm);

    public abstract SendMessage sendMessageToAll(SendMessage sm);


}
