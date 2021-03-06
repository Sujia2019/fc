package com.easyarch.model.code;

public class CODE {

    /*
    注册
     */
    public static final int REGIST = 0;
    /*
    登录
     */
    public static final int LOGIN = 1;
    /*
    更新
    */
    public static final int UPDATE = 2;
    /*
    备份数据
    */
    public static final int SAVE = 3;
    /*
    短信验证码注册
     */
    public static final int REGIST_PHONE=4;
    /*
    短信验证码登录
     */
    public static final int LOGIN_PHONE=5;

    /*
    验证码状态
     */
    public static final int SEND = 6;
    public static final int VERIFY = 7;
    /*
    用户
     */
    public static final int USER_TYPE = 10;
//-------------------------------------------------------------------
    /*
    发送消息
     */
    public static final int MESSAGE = 11;
    /*
    群组系统
     */
    public static final int GROUP = 12;
    /*
    好友系统
     */
    public static final int FRIEND = 13;

    public static final int CHAT_TYPE = 20;
    /*
    购买物品
     */
    public static final int BUY = 24;
    /*
    卖出物品
     */
    public static final int SELL = 25;

//---------------------------------------------------------------
    /*
    成功返回
     */
    public static final int SUCCESS = 666;
    /*
    错误返回
     */
    public static final int ERROR = 400;
    /*
    请重新发送
     */
    public static final int RETRY = 401;
    /*
    返回
    */
    public static final int RETURN = 999;
//---------------------------------------------------------------
    /*
    打怪
     */
    public static final int FIGHT = 50;//初始化
    /*
    胜利
     */
    public static final int WIN = 51;
    /*
    失败
     */
    public static final int DEFEAT = 52;
//-----------------------------------------------------------------
    /*
    玩家匹配
     */
    public static final int MATCH = 505;
    /*
    获得敌人
     */
    public static final int ENEMY = 506;
    /*
    匹配成功后开启决斗
     */
    public static final int MATCH_FIGHT= 507;
    /*
    匹配等待
     */
    public static final int MATCH_WAIT = 508;//匹配等待
    /*
    匹配失败
     */
    public static final int MATCH_FAIL = 509;//匹配失败的代码

    public static final int MATCH_TYPE = 600;
//-----------------------------------------------------------------
    /*
    心跳
     */
    public static final int PING = 120;

//-----------------------------------------------------------------
    //推送
    /*
    系统提示
     */
    public static final int PUSH_GM = 9999;
    /*
    活动推送
     */
    public static final int PUSH_ACTIVITY=10000;
    /*
    普通消息
     */
    public static final int PUSH_NORMAL = 10001;
    /*
    VIP消息
     */
    public static final int PUSH_VIP = 10002;



}
