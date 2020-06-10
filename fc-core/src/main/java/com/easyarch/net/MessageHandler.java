package com.easyarch.net;

import com.easyarch.cache.Maps;
import com.easyarch.invoker.MessageInvoker;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import com.easyarch.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<Message>{
    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    @Autowired
    private MessageInvoker invoker ;

    private static ExecutorService pool = Executors.newFixedThreadPool(20);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        int code = msg.getMsgCode();

        logger.info("传入【{}】信息,读取对象【{}】",code,msg.getObj());

//        pool.execute(new Runnable() {
//            @Override
//            public void run() {
//                ctx.writeAndFlush(invoker.handle(ctx,msg));
//            }
//        });

//        Maps.group.writeAndFlush(msg);
        msg = invoker.handle(ctx,msg);
        ctx.writeAndFlush(msg);
        logger.info("---发送信息【{}】---",msg);

    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /*
        推送服务
        系统广播
         */
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("------用户注册------");
//        Maps.group.add(ctx.channel());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("------用户离线------");
        super.channelUnregistered(ctx);
    }
}
