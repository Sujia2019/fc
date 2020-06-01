package com.easyarch.net;

import com.easyarch.model.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyPromoteClient {
    private ChannelFuture future;
    private static ExecutorService executor = Executors.newSingleThreadExecutor();
    public NettyPromoteClient(){
        init();
    }
    private void init() {
        Bootstrap client = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        client.group(group);

        client.channel(NioSocketChannel.class);

        client.handler(new NettyClientInitializer()).option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
        try {
            future = client.connect("47.93.225.242",8888).sync();
            System.out.println("------connect------");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    future.channel().writeAndFlush(message).sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
