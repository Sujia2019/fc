package com.easyarch.invoker;

import com.easyarch.api.*;
import com.easyarch.api.chat.ChatFactory;
import com.easyarch.api.exception.ExceptionFactory;
import io.netty.channel.ChannelHandlerContext;

import com.easyarch.model.Message;
import com.easyarch.model.code.CODE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MessageInvoker {

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
            msg = userFactory.handle(msg);
        }
        else if (code <= CODE.CHAT_TYPE){
            msg = chatFactory.handle(msg);
        }
        //打机器人
        else if (code == CODE.FIGHT) {
            msg = monsterFactory.handle(msg);
        }
        //匹配
        else if (code <= CODE.MATCH_TYPE) {
            msg = matchFactory.handle(msg);
        }

        else {
            msg = exceptionFactory.handle(msg);
        }

        return msg;
    }

}
