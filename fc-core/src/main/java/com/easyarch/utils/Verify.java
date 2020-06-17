package com.easyarch.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Verify {
    private static final Logger logger = LoggerFactory.getLogger(Verify.class);


    public static void sendCode(String phoneNumber ){
        int codeInt = (int)((Math.random()*9+1)*100000);
        String i = String.valueOf(codeInt);

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "LTAI4G1zPmUzngP2545Hs8pE", "hnoNNYHi6Z2NrfhEn51agnqwuKIxLl");
        IAcsClient client = new DefaultAcsClient(profile);


        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "18539403150");
        request.putQueryParameter("SignName", "灵魂决斗");
        request.putQueryParameter("TemplateCode", "SMS_189611057");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+i+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            logger.info("------输出发送验证码的信息【{}】------",response.getData());
            //向缓存中加入验证码信息
            RedisUtil.insertCode(phoneNumber,i);

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
//    public static void main(String[] args) {
//        sendCode("13503844807");
//    }

}
