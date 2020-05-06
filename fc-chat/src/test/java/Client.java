import com.easyarch.NettyClient;
import com.easyarch.model.Message;
import com.easyarch.model.UserInfo;
import com.easyarch.model.chat.SendMessage;
import com.easyarch.model.chat.Type.MsgType;
import com.easyarch.model.code.CODE;
import org.junit.Before;
import org.junit.Test;

public class Client {
    private NettyClient client;

    @Before
    public void connect(){

        client = new NettyClient();
    }

    @Test
    public void regist(){
        UserInfo us = new UserInfo();
        us.setUserId("111111");
        us.setUserPwd("123456");
//        us.setUserName("test1");
        Message message = new Message(CODE.REGIST,us);
        client.sendMessage(message);
    }

    @Test
    public void login(){

        UserInfo us = new UserInfo();
        us.setUserId("222222");
        us.setUserPwd("123456");
//        us.setUserName("test2");
        Message message = new Message(CODE.LOGIN,us);
        client.sendMessage(message);
    }

    @Test
    public void chat(){
        SendMessage m = new SendMessage();
        m.setFromId("xxx");
        m.setType(MsgType.ALL);
        m.setMsg("+++");
        Message message = new Message(CODE.REGIST,m);
        client.sendMessage(message);
    }


}
