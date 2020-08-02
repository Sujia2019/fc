package com.easyarch.api.service;

import com.easyarch.model.Message;
import com.easyarch.model.PushModel;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 从kafka消息队列中拿服务事件
 */
public abstract class GetRemoteService<T> {
    @Resource(name = "consumerProps")
    Map<String, Object> propsMap;
    /**
     * 获取
     * @return
     */
    abstract T get();

    /**
     * 处理消息
     * @return
     */
    abstract Object handle(T msg);

    /**
     * 设置容器属性
     * @param listener 监听方法
     * @return
     */
    public abstract ContainerProperties setContainerProps(MessageListener<Integer, T> listener);

    public abstract MessageListener<Integer,T> setMessageListener();

    public KafkaMessageListenerContainer<Integer, Message> createContainer(
            ContainerProperties containerProps) {
        Map<String, Object> props = propsMap;
        DefaultKafkaConsumerFactory<Integer, Message> cf =
                new DefaultKafkaConsumerFactory<Integer, Message>(props);
        KafkaMessageListenerContainer<Integer, Message> container =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        return container;
    }
}
