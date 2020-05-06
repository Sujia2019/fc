package com.easyarch.cache;

import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Maps {

    //群组
    public static Map<String, ChannelGroup> groupMap = new ConcurrentHashMap<>();

    //在线channel组
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //用户和channelId
    public static Map<String, ChannelId> userMap = new ConcurrentHashMap<>();

    //范围 匹配
    public static Map<Integer, LinkedBlockingQueue<String>> map = new ConcurrentHashMap<>();


}
