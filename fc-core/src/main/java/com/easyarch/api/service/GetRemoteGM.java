package com.easyarch.api.service;

import com.easyarch.model.Message;
import com.easyarch.statics.StaticConfigs;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

public class GetRemoteGM extends GetRemoteService<Message> {
    @Override
    Message get() {
        return null;
    }

    @Override
    Object handle(Message msg) {
        return null;
    }

    @Override
    public ContainerProperties setContainerProps(MessageListener<Integer, Message> listener){
        ContainerProperties containerProps = new ContainerProperties(
                StaticConfigs.TOPIC_PUSH_GM);
        containerProps.setMessageListener(listener);
        return containerProps;
    }

    @Override
    public MessageListener<Integer, Message> setMessageListener() {
        return null;
    }
}
