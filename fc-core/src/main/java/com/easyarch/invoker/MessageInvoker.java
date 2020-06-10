package com.easyarch.invoker;

import com.easyarch.api.*;
import com.easyarch.api.chat.ChatFactory;
import com.easyarch.api.exception.ExceptionFactory;
import com.easyarch.net.MessageHandler;
import io.netty.channel.ChannelHandlerContext;

import com.easyarch.model.Message;
import com.easyarch.model.code.CODE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MessageInvoker {
    private static final Logger logger = LoggerFactory.getLogger(MessageInvoker.class);

//    public Message handle(ChannelHandlerContext ctx, Message msg){
//        int code = msg.getMsgCode();
//        MessageAbstractFactory factory;
//
//        //用户操作
//        if(code<= CODE.USER_TYPE){
//            factory = new UserFactory();
//            factory.setCtx(ctx);
//        }
//        //打架
//        else if (code == CODE.FIGHT){
//            factory = new MonsterFactory();
//        }
//        //匹配
//        else if(code <= CODE.MATCH_TYPE){
//            factory = new MatchFactory();
//        }
//        //异常
//        else{
//            factory = new ExceptionFactory();
//        }
//
//        msg = factory.handle(msg);
//        return msg;
//    }


    @Autowired
    private UserFactory userFactory;

    @Autowired
    private MonsterFactory monsterFactory;

    @Autowired
    private MatchFactory matchFactory;

    @Autowired
    private ExceptionFactory exceptionFactory;

    @Autowired
    private ChatFactory chatFactory;

    public Message handle(ChannelHandlerContext ctx, Message msg) {
        int code = msg.getMsgCode();

        if (code <= CODE.USER_TYPE) {
            userFactory.setCtx(ctx);
            logger.info("------进入UserFactory------");
            msg = userFactory.handle(msg);
        }
        else if (code <= CODE.CHAT_TYPE){
            logger.info("------进入ChatFactory------");
            msg = chatFactory.handle(msg);
        }
        //打机器人
        else if (code == CODE.FIGHT) {
            logger.info("------进入MonsterFactory------");
            msg = monsterFactory.handle(msg);
        }
        //匹配
        else if (code <= CODE.MATCH_TYPE) {
            logger.info("------进入MatchFactory------");
            msg = matchFactory.handle(msg);
        }

        else {
            logger.info("------EXCEPTION------");
            msg = exceptionFactory.handle(msg);
        }

        return msg;
    }

}
