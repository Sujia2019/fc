package com.easyarch.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class Verify {

    public static void sendCode(String phoneNumber ){
        int codeInt = (int)((Math.random()*9+1)*100000);
        String i = String.valueOf(codeInt);

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "your-accesskey", "your-accesskey-password");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "xxx");
        request.putQueryParameter("TemplateCode", "SMS_xxx");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+i+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        RedisUtil.insertCode(phoneNumber,i);
    }
//    public static void main(String[] args) {
//        sendCode("18539403150");
//    }

}
