package com.easyarch.factory;

import com.easyarch.api.MessageFactory;
import com.easyarch.model.Message;
import io.netty.channel.ChannelHandlerContext;

public abstract class MessageAbstractFactory implements MessageFactory {

    ChannelHandlerContext ctx;
    /*
    必须实现的方法
     */
    public abstract Message handle(Message msg);


//    @Autowired
    MessageAbstractFactory(){
        /*
        ...
        ...
         */
    }

    public void setCtx(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }

    //每个工厂都会有的一些方法不必抽象
    void logger(){
        /*
        日志...
         */
    }
    /*
    ...
    ...
    ...
     */

    void exception(){

    }
}
