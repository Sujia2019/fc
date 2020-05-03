package function;


import com.easyarch.model.CodeRequest;
import com.easyarch.model.Message;
import com.easyarch.model.UserInfo;
import com.easyarch.model.code.CODE;

import java.util.Scanner;

public class TestVerify {
    public static void main(String[] args) {
        NettyClient client = new NettyClient();

        Message message = new Message();
        message.setMsgCode(CODE.LOGIN_PHONE);

        //验证码请求
        CodeRequest request = new CodeRequest();
        request.setPhoneNumber("18539403150");
        request.setStatus(CODE.VERIFY);
        request.setCode("910356");

        message.setObj(request);
        client.sendMessage(message);

//        Scanner sc = new Scanner(System.in);
//        String code = sc.next();
//        if(code!=null){
//            request.setCode(code);
//            request.setStatus(CODE.VERIFY);
//        }
    }
}
