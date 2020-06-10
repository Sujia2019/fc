package com.easyarch.api;

import com.easyarch.model.Attribute;
import com.easyarch.model.Message;
import com.easyarch.model.Operation;
import com.easyarch.model.Robot;
import com.easyarch.model.code.Action;
import com.easyarch.model.code.BUFF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class MonsterFactory extends MessageAbstractFactory {
    private static final Logger logger = LoggerFactory.getLogger(MonsterFactory.class);


    private Robot robot;
    private int level ;
    private int react ;
    private String userId;

    @Override
    public Message handle(Message msg) {
        Operation op = (Operation)msg.getObj();
        level = op.getLevel();
        react = op.getAction();
        userId = op.getAttribute().getUserId();
        logger.info("---获取玩家【{}】等级【{}】和操作【{}】",userId,level,react);
        //start
        if(Action.START==react&&null==robot){
            this.robot = new Robot(level);
            op.setRobot(robot);
        }else if(robot!=null){
            Attribute attr = op.getAttribute();
            int action = op.getAction();

            op.setAttribute(fightRound(attr, action));
            op.setRobot(robot);
            op.setAction(react);
        }else{
            logger.info("---【{}】操作不合法!【{}】",userId,op.toString());
            op.setAction(Action.INVALID);
        }
        msg.setObj(op);
        return msg;
    }

    //战斗回合
    private Attribute fightRound(Attribute player, int action){
        if(robot==null){
            return player;
        }else{
            int pdf = player.getDef();
            int pat = player.getAttack();
            int php = player.getHp();
            logger.info("---【{}】获取玩家信息：防御值【{}】 -攻击力【{}】 -血量【{}】---",userId,pdf,pat,php);
            if(action == Action.ATTACK){
                if(robot.getAttack()<=pdf){
                    //如果防御大于机器人的攻击，则不受伤害
                    logger.info("---机器人的攻击【{}】,玩家【{}】的防御【{}】防御大于攻击，不受伤害!---",robot.getAttack(),userId,pdf);
                    react = Action.END;
                    return player;
                }else{
                    int damage = robot.getAttack()-pdf;
                    php -= damage;
                    if(php<=0){
                        //玩家失败
                        react = Action.DEATH;
                        robot = new Robot(level);
                        logger.info("---【{}】玩家失败---",userId);
                        return player;
                    }

                    int rhp = robot.getHp()-pat;
                    if(rhp<=0){
                        //结束
                        react = Action.END;
                        robot = null;
                        logger.info("---【{}】玩家胜利---",userId);
                        return player;
                    }else{
//                        react=Action.ATTACK;
                        robot.setHp(rhp);
                        player.setHp(php);
                        logger.info("---玩家【{}】,受到【{}】伤害,玩家剩余血量【{}】,机器人剩余血量【{}】---",userId,damage,php,rhp);
                        return player;
                    }
                }
            }

            if(action==Action.BUFF){
                //简单做了个+攻击力的buff....
                pat += BUFF.BUFF_ADD_LITTLE;
                player.setAttack(pat);
                logger.info("---玩家【{}】给自己加了BUFF【{}】",userId,pat);
                return player;
            }
        }
        logger.info("---【{}】操作不合法!",userId);
        react = Action.INVALID;
        return player;
    }

}
