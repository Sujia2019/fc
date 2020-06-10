package com.easyarch.api;

import com.easyarch.cache.Maps;
import com.easyarch.model.Message;
import com.easyarch.model.Operation;
import com.easyarch.model.code.CODE;
import com.easyarch.utils.RedisUtil;
import io.netty.channel.ChannelId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class MatchFactory extends MessageAbstractFactory implements MatchMethod {
    private static final Logger logger = LoggerFactory.getLogger(MatchFactory.class);

    //范围数组
    private static Map<Integer, LinkedBlockingQueue<String>> map = new ConcurrentHashMap<>();
    //加一个监控队列中对象的信息
    //private static ExecutorService listen

    @Override
    public Message handle(Message msg) {
        int code = msg.getMsgCode();
        if(code == CODE.MATCH){
            return handleMatch(msg);
        }else if(code == CODE.MATCH_FIGHT){
            return handleFight(msg);
        }else if(code == CODE.MATCH_FAIL){
            return handleFail(msg);
        }
        return null;
    }
    private Message handleFight(Message msg){
        Object obj = msg.getObj();
        String enemy = ((Operation)obj).getEnemyId();
        ChannelId enemyId = Maps.userMap.get(enemy);
        //将你的操作直接发给敌人的客户端去处理
        Maps.group.find(enemyId).writeAndFlush(msg);
        Message m = new Message();
        m.setMsgCode(CODE.SUCCESS);
        m.setObj("SUCCESS");
        logger.info("------处理战斗数据------");
        return m;
    }

    private Message handleFail(Message msg){

        String self = (String)msg.getObj();
        int rank = getRank(self);

        try{
            //如果取消有异常
            cancel(rank,self);
            msg.setMsgCode(CODE.SUCCESS);
            msg.setObj("取消匹配!");
            logger.info("玩家【{}】取消匹配",self);
        }catch (Exception e){
            //告诉客户端重新发送
            msg.setMsgCode(CODE.RETRY);
            msg.setObj(e.getMessage());
            logger.info("玩家【{}】取消匹配异常",self);
            return msg;
        }
        return msg;
    }
    private int getRank(String self){
        String sRank = RedisUtil.getConnection().hget(self,RedisUtil.RANK);
        return Integer.parseInt(sRank);
    }

    private Message handleMatch(Message msg){
        logger.info("------开始匹配------");
        String self = (String)(msg.getObj());

        //获取隐藏分
        int rank = getRank(self);
        logger.info("------获取玩家【{}】的隐藏分Rank【{}】!------",self,rank);

        //等于只找一次，若没有结果则会停留在等待队列中
        String enemy = match(rank,self);

        if(null==enemy||self.equals(enemy)){
            //告诉客户端等待
            logger.info("------玩家【{}】等待------",self);
            msg.setMsgCode(CODE.MATCH_WAIT);

        }else{
            //匹配到了
            msg.setObj(enemy);
            msg.setMsgCode(CODE.ENEMY);
            //将你自己的id告诉对手
            Message toEnemy = new Message();
            toEnemy.setMsgCode(CODE.ENEMY);
            toEnemy.setObj(self);
            Maps.group.find(Maps.userMap.get(enemy)).writeAndFlush(toEnemy);
        }
        return msg;
    }


    @Override
    public void cancel(int rank, String userId) {
        map.get(rank).remove(userId);
        logger.info("------玩家【{}】取消匹配!------",userId);
    }

    @Override
    public void cancel(String userId) {

    }


    private void add(int rank, String userId) {
        if(map.containsKey(rank)){
            map.get(rank).add(userId);
        }else{
            LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
            queue.add(userId);
            map.put(rank,queue);
//            map.put(rank,new LinkedBlockingQueue<>());
        }
        logger.info("------玩家【{}】进入匹配等待队列------",userId);
    }

    @Override
    public String match(int rank,String userId) {
        boolean up = true;
        for(int i=rank,k = 1;k<=15;k++){
            if(map.containsKey(i)){
                try{
                    String enemyId;
                    enemyId = map.get(i).take();
                    logger.info("------玩家【{}】匹配到对手【{}】------",userId,enemyId);
                    return enemyId;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //中心扩散法
            if(up){
                i=i+k;
                up = false;
            }else{
                i=i-k;
                up = true;
            }
        }
        //没有匹配到则进入队列
        add(rank,userId);
        return null;
    }


}
