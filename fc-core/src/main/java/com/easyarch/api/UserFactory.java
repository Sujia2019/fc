package com.easyarch.api;

import com.easyarch.cache.Maps;
import com.easyarch.dao.mapper.GroupDao;
import com.easyarch.dao.mapper.UserMapper;
import com.easyarch.model.CodeRequest;
import com.easyarch.model.Message;
import com.easyarch.model.PlayerInfo;
import com.easyarch.model.UserInfo;
import com.easyarch.model.code.CODE;
import com.easyarch.utils.RedisUtil;
import com.easyarch.utils.Verify;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
    处理用户业务
 */

@Component
public class UserFactory extends MessageAbstractFactory{

    private static final Logger logger = LoggerFactory.getLogger(UserFactory.class);


    @Autowired
    private UserMapper dao ;

    @Autowired
    private GroupDao groupDao;

//    private UserDaoImp dao = new UserDaoImp();

    @Override
    public Message handle(Message msg) {
        int code = msg.getMsgCode();
        if(code == CODE.LOGIN){
            return handleLogin(ctx,msg);
        }else if(code == CODE.REGIST){
            return handleRegist(ctx,msg);
        }else if (code == CODE.REGIST_PHONE){
            return handleCodeRegist(msg);
        }else if(code ==  CODE.LOGIN_PHONE){
            return handleCodeLogin(msg);
        }else if(code == CODE.UPDATE){
            return handleUpdate(msg);
        }else if(code == CODE.SAVE){
            return handleSave(msg);
        }else{
            logger.info("------业务转发异常------");

            msg.setObj("ERROR");
        }
        return msg;
    }

    /*
    普通的用户名密码注册
     */
    private Message handleRegist(ChannelHandlerContext ctx, Message msg){
        logger.info("------进入普通注册------");
        UserInfo us = (UserInfo) msg.getObj();
        if(regist(us)){
            //初始化玩家信息
            playerInit(us.getUserId());
            //先存MySQL---再存redis---登录(先访问redis---再访问数据库)
            return handleLogin(ctx,msg);
        }
        msg.setMsgCode(CODE.REGIST);
        msg.setObj("注册失败,此id已注册");
        return msg;
    }
    /*
    普通的用户名密码登录
     */
    private Message handleLogin(ChannelHandlerContext ctx, Message msg){
        logger.info("------进入普通登录------");
        UserInfo us = (UserInfo) msg.getObj();
        if(null!=login(us)){
            String userId = us.getUserId();

            PlayerInfo player = getPlayer(userId);
            logger.info("------获取玩家信息【{}】------",player);

            load(userId);
            msg.setObj(player);
        }else{
            logger.info("------登录失败，用户名或密码错误------");
            msg.setObj("登录失败,用户名或密码错误");
        }
        return msg;
    }

    private Message handleCodeRegist(Message msg){
        logger.info("------进入验证码注册登录------");
        CodeRequest request = (CodeRequest)msg.getObj();
        if(request.getStatus()==CODE.SEND){
            logger.info("---【注册】---发送验证码------");
            msg.setObj(sendRegistCode(request.getPhoneNumber()));
        }
        else if(request.getStatus()==CODE.VERIFY){
            logger.info("---【注册】---校验验证码------");
            msg.setObj(codeRegist(request.getPhoneNumber(),request.getCode()));
        }else{
            logger.info("---【注册】---验证码错误------");
            msg.setObj("Error");
        }
        return msg;

    }
    private Message handleCodeLogin(Message msg){
        CodeRequest request = (CodeRequest)msg.getObj();
        logger.info("------进入验证码登录------");
        if(request.getStatus()==CODE.SEND){
            logger.info("---【登录】---发送验证码------");
            msg.setObj(sendLoginCode(request.getPhoneNumber()));
        }
        else if(request.getStatus()==CODE.VERIFY){
            logger.info("---【登录】---校验验证码------");
            PlayerInfo player = codeLogin(request.getPhoneNumber(),request.getCode());
            if(null!=player){
                logger.info("---【登录】---获取玩家信息【{}】------",player);
//                Maps.userMap.put(request.getPhoneNumber(),ctx.channel().id());
                load(request.getPhoneNumber());
                msg.setObj(player);
            }else{
                logger.info("---【登录】---验证码错误------");
                msg.setObj("Error");
            }
        }else{
            msg.setObj("Error");
        }
        return msg;
    }

    /*
    发送验证码
    思路，前端不论验证码登录还是验证码注册，先带着电话号
    先判断是否有这个用户---再进行业务
    发送成功后将电话号和code存redis，用户第二次输入验证码发的是UserInfo，userId就是电话号，密码就是code，
    但实际存数据库的是随机数，需要用户自己重置密码
     */
    private String sendRegistCode(String phoneNumber){
        //如果用户不存在
        if(!isUser(phoneNumber)){
            //发送验证码

            Verify.sendCode(phoneNumber);
            return "发送成功";
        }else{
            return "发送失败，该手机号已注册！";
        }
    }
    /*
    验证码是否正确
     */
    private boolean isCodeCorrect(String userId,String code){
        if(RedisUtil.isContainsKey(userId)){
            String codeS = RedisUtil.getConnection().get(userId);
            return codeS.equals(code);
        }
        return false;
    }

    private String sendLoginCode(String phoneNumber){
        //如果用户存在
        if(isUser(phoneNumber)){
            //发送验证码
            Verify.sendCode(phoneNumber);
            return "发送成功";
        }else{
            return "发送失败，该手机号未绑定用户！";
        }
    }

    private Object codeRegist(String phoneNumber,String code){
//        String userId = ()
//        UserInfo user = (CodeRequest)msg.getObj();
        //如果验证码正确
        if(isCodeCorrect(phoneNumber,code)){
            RedisUtil.removeKey(phoneNumber);
            //注册
            UserInfo user = new UserInfo();
            user.setUserId(phoneNumber);
            user.setUserPwd(randomPwd());
            regist(user);
            logger.info("------用户【{}】验证码校验成功------",phoneNumber);
            return playerInit(phoneNumber);
        }else{
            return "验证码错误";
        }

    }

    private PlayerInfo codeLogin(String phoneNumber,String code){
        //如果验证码正确
        if(isCodeCorrect(phoneNumber, code)){
            RedisUtil.removeKey(phoneNumber);
            return getPlayer(phoneNumber);
        }
        else{
            return null;
        }
    }


    /*
    @Before
     */
    private String login(UserInfo user) {
        System.out.println(user.getUserId());
        if(isUser(user.getUserId())){
            user = setMd5Hex(user);
            UserInfo userInfo = dao.searchUserByUserInfo(user);
            return userInfo.getUserId();
        }
        return null;
    }

    /*
    @After
     */
    private boolean regist(UserInfo user) {
        if (!isUser(user.getUserId())){
            user = setMd5Hex(user);
            return dao.insertUser(user) == 1;
        }
        return false;
    }

    private boolean isUser(String id){
        String xxx = DigestUtils.md5Hex(id);
        return dao.searchById(xxx) != 0;
    }

    /*
    初始化玩家信息
     */
    private PlayerInfo playerInit(String id){

        PlayerInfo player = new PlayerInfo();
        player.setUserId(id);
        player.setUserName("test");
        player.setRank(10);

        //数据库初始化玩家信息
//        dao.insertPlayer(player);
        if(0!=dao.insertPlayer(player)){
//            redis缓存一份
            RedisUtil.updatePlayer(player);
        }
        logger.info("------加载玩家【{}】信息------",player.getUserId());
        return player;
//
    }

    private boolean updatePlayer(PlayerInfo player){
        return dao.updatePlayer(player) != 0;
    }

    private PlayerInfo getPlayer(String userId){
        PlayerInfo player = null;
         //如果在redis里
        if(RedisUtil.isContainsKey(userId)){
            player = RedisUtil.getPlayer(userId);
            logger.info("------从缓存Redis中获取玩家信息【{}】------",player);

        }
        //如果没在redis里
        else{
            player = dao.getPlayer(userId);
            //更新缓存
            RedisUtil.updatePlayer(player);
            logger.info("------从MySQL中获取玩家信息【{}】------",player);
        }
        return player;
    }

    /*
    更新玩家信息
     */
    private Message handleUpdate(Message msg){
        PlayerInfo player = (PlayerInfo)msg.getObj();
//
        if(!RedisUtil.updatePlayer(player)){
            logger.info("------更新失败【{}】------",player);
            msg.setObj("更新失败");
        }
        return msg;
    }

    /*
    保存
     */
    private Message handleSave(Message msg){
        Object obj = msg.getObj();
        String userId = (String) obj;
        PlayerInfo playerInfo = RedisUtil.getPlayer(userId);
        if(updatePlayer(playerInfo)){
            msg.setMsgCode(CODE.SUCCESS);
            msg.setObj("保存成功！");
            logger.info("------保存成功【{}】------",playerInfo);
            return msg;
        }
        msg.setMsgCode(CODE.ERROR);
        logger.info("------保存失败【{}】------",playerInfo);
        msg.setObj("保存失败");
        return msg;
    }

    /*
    设置密码
     */
    private void handleSetPwd(String userId,String pwd){
        dao.updateUserPwd(DigestUtils.md5Hex(userId),DigestUtils.md5Hex(pwd));
    }

    /*
    数据加密
     */
    private UserInfo setMd5Hex(UserInfo user){
        UserInfo userInfo = new UserInfo();
        String pwd = DigestUtils.md5Hex(user.getUserPwd());
        String id = DigestUtils.md5Hex(user.getUserId());
        userInfo.setUserId(id);
        userInfo.setUserPwd(pwd);
        return userInfo;
    }

    private String randomPwd(){
        int codeInt = (int)((Math.random()*9+1)*10000000);
        return String.valueOf(codeInt);
    }

    private void load(String id){
        logger.info("------加载【{}】玩家------",id);

        Maps.userMap.put(id,ctx.channel().id());
        Maps.group.add(ctx.channel());
        //查询玩家的群组 这里有堵塞 不知道什么问题 待解决
//        groupDao.searchGroup(id);
    }
}
