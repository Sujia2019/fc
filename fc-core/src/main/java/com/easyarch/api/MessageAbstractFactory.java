package com.easyarch.api;

import com.easyarch.model.Message;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
public abstract class MessageAbstractFactory implements MessageFactory {

    ChannelHandlerContext ctx;
    /*
    必须实现的方法
     */
    public abstract Message handle(Message msg);


//    @Autowired
    public MessageAbstractFactory(){
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
