package com.easyarch.model;

import lombok.Data;

/**
 * 策划将给客户端推送的消息
 */
@Data
public class PushModel {
    private int pushCode;
    private int pushId;//推送消息的id
    private Object event;//要推送的事
}
