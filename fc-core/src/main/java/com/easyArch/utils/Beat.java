package com.easyArch.utils;

import com.easyArch.model.Message;
import com.easyArch.model.code.CODE;

public class Beat {

    public static final int BEAT_INTERVAL = 30;
    public static final String BEAT_ID="BEAT_PING_PONG";
    public static Message BEAT_PING;
    static{
        BEAT_PING=new Message();
        BEAT_PING.setObj(BEAT_ID);
        BEAT_PING.setMsgCode(CODE.PING);
    }
}
