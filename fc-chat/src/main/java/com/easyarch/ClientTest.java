package com.easyarch;


import com.easyarch.model.Message;
import com.easyarch.model.UserInfo;
import com.easyarch.model.code.CODE;

public class ClientTest {
    public static void main(String[] args) {

         NettyClient client = new NettyClient();

         Message message = new Message();
//         message.setMsgCode(CODE.MESSAGE);
//         SendMessage msg = new SendMessage();
//         msg.setFromId("184500237");
//         msg.setType(MsgType.ALL);
//         msg.setMsg("Hello!");
//         message.setObj(msg);

            UserInfo us = new UserInfo();
            us.setUserId("444444");
            us.setUserPwd("123456");
//            us.setUserName("test4");
            message.setMsgCode(CODE.REGIST);
            message.setObj(us);

         client.sendMessage(message);

    }
}
