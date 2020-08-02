package com.easyarch.utils;

import com.easyarch.model.Message;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class GsonSerializer implements Deserializer<Message> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Message deserialize(String s, byte[] bytes) {
        return null;
    }

    @Override
    public Message deserialize(String topic, Headers headers, byte[] data) {
        return null;
    }

    @Override
    public void close() {

    }
}
