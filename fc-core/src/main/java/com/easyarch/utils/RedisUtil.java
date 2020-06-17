package com.easyarch.utils;
import com.easyarch.model.PlayerInfo;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

//@Configuration
@PropertySource("classpath:config.properties")
public class RedisUtil {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    private static StatefulRedisConnection<String,String> connection;
    private static RedisUtil redisUtil = new RedisUtil();
    private static RedisCommands<String,String> commands ;

    /*
    为啥一获取就是空呢！
     */
    @Value("${server.ip}")
    private  String host;

    @Value("#{${redis.port}}")
    private  int port;

    private RedisUtil(){
        RedisURI uri = RedisURI.builder()
                .withHost("localhost")
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();                                         //创建单机连接信息

        RedisClient client = RedisClient.create(uri);                         //创建客户端
        connection = client.connect();                            //创建线程安全
        commands = getConnection();
    }

//    @PostConstruct
//    public void init(){
//        RedisURI uri = RedisURI.builder()
//                .withHost(host)
//                .withPort(port)
//                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
//                .build();                                         //创建单机连接信息
//
//        RedisClient client = RedisClient.create(uri);                         //创建客户端
//        connection = client.connect();                            //创建线程安全
//        commands = getConnection();
//    }



//    @Bean
    public static RedisCommands<String,String> getConnection(){
        return connection.sync();
    }

    public static Boolean updatePlayer(PlayerInfo playerInfo){
        String userId = playerInfo.getUserId();
        logger.info("------向Redis中更新玩家数据【{}】------",playerInfo);
        return
                commands.hset(userId,USERNAME,playerInfo.getUserName())&&
                commands.hset(userId,FIGHTCOUNT,""+playerInfo.getFightCount())&&
                commands.hset(userId,WINCOUNT,""+playerInfo.getWinCount())&&
                commands.hset(userId,MONEY,""+playerInfo.getMoney())&&
                commands.hset(userId,RANK,""+playerInfo.getRank())&&
                commands.hset(userId,CLIMBLEVEL,""+playerInfo.getClimbLevel())&&
                commands.expire(userId,60*60*24*7);
        //7天过期时间

    }

    public static void updatePlayerAttribute(String userId,String field,String value){
        commands.hset(userId,field,value);
    }

    public static RedisCommands<String, String> getRedisConnection() {
        RedisClient redisClient = RedisClient.create("redis://localhost");
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        return connect.sync();
    }

    public static void initPlayer(String userId){
        commands.hset(userId,USERNAME,userId);
        commands.hset(userId,FIGHTCOUNT,"0");
        commands.hset(userId,WINCOUNT,"0");
        commands.hset(userId,MONEY,"0");
        commands.hset(userId,CLIMBLEVEL,"0");
        commands.hset(userId,RANK,"10");
        commands.expire(userId,60*60*24*7);
    }

    public static PlayerInfo getPlayer(String userId){
        //这也应该注入

        PlayerInfo player  = new PlayerInfo();

        String userName = commands.hget(userId,USERNAME);
        String fightCount = commands.hget(userId,FIGHTCOUNT);
        String money = commands.hget(userId, MONEY);
        String rank = commands.hget(userId,RANK);
        String climbLevel = commands.hget(userId,CLIMBLEVEL);
        String winCount = commands.hget(userId,WINCOUNT);

        player.setUserId(userId);
        player.setUserName(userName);
        player.setFightCount(Integer.valueOf(fightCount));
        player.setRank(Integer.valueOf(rank));
        player.setClimbLevel(Integer.valueOf(climbLevel));
        player.setWinCount(Integer.valueOf(winCount));
        player.setMoney(Integer.valueOf(money));
        return player;
    }

    public static boolean isContainsKey(String key){
        long x = commands.exists(key);
        return x != 0;
    }

    public static void insertCode(String phoneNumber,String code){
        commands.set(phoneNumber,code);
        commands.expire(phoneNumber,60*5);
    }

    public static void removeKey(String key){
        commands.del(key);
    }


    public static final String USERNAME = "userName";
    public static final String USERID = "userId";
    public static final String MONEY = "money";
    public static final String WINCOUNT = "winCount";
    public static final String CLIMBLEVEL = "climbLevel";
    public static final String RANK = "rank";
    public static final String FIGHTCOUNT = "fightCount";

}
