package com.easyarch.net;

import com.easyarch.model.Message;
import com.easyarch.serialize.imp.ProtoStuffSerializer;
import com.easyarch.utils.Beat;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new IdleStateHandler(0, 0,
                Beat.BEAT_INTERVAL * 3, TimeUnit.SECONDS));
        ch.pipeline().addLast(new NettyEncoder(Message.class,new ProtoStuffSerializer()));
        ch.pipeline().addLast(new NettyDecoder(Message.class,new ProtoStuffSerializer()));
        ch.pipeline().addLast(new SimplePromoteHandler());
    }
}
