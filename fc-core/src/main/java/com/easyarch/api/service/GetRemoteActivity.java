package com.easyarch.api.service;

import com.easyarch.model.Message;
import com.easyarch.statics.StaticConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class GetRemoteActivity extends GetRemoteService<Message>{
    private static final Logger logger = LoggerFactory.getLogger(GetRemoteActivity.class);
    public GetRemoteActivity(){
    }

    @Override
    public Message get() {
        return null;
    }

    @Override
    public Object handle(Message msg) {
        return null;
    }
    //消费者监听容器
    @Override
    public ContainerProperties setContainerProps(MessageListener<Integer,Message> listener){
        ContainerProperties containerProps = new ContainerProperties(
                StaticConfigs.TOPIC_PUSH_ACTIVITY);
        containerProps.setMessageListener(listener);
        return containerProps;
    }

    @Override
    public MessageListener<Integer, Message> setMessageListener() {
        return msg -> {
            /*
             * 获取队列中的消息的方法
             */
            logger.info("receiver:---key:【{}】,value:【{}】",msg.key(),msg.value());
            //处理
            handle(msg.value());
        };
    }


}
