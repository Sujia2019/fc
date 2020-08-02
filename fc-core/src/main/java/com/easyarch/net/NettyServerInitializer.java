package com.easyarch.net;

import com.easyarch.model.Message;
import com.easyarch.serialize.imp.GsonSerializer;
import com.easyarch.serialize.imp.ProtoStuffSerializer;
import com.easyarch.utils.Beat;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {


    @Autowired
    private MessageHandler messageHandler;


    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(0, 0,
                Beat.BEAT_INTERVAL * 3, TimeUnit.SECONDS));
        pipeline.addLast(new NettyDecoder(Message.class,new GsonSerializer()));
        pipeline.addLast(new NettyEncoder(Message.class,new GsonSerializer()));

        pipeline.addLast(messageHandler);



    }
}
