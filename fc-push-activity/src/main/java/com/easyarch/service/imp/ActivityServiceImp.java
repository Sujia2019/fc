package com.easyarch.service.imp;

import com.easyarch.dao.ActivityMapper;
import com.easyarch.entity.Activity;
import com.easyarch.model.Message;
import com.easyarch.model.code.CODE;
import com.easyarch.service.ActivityService;
import com.easyarch.statics.StaticConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImp implements ActivityService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImp.class);
    @Autowired
    ActivityMapper mapper;
    @Autowired
    KafkaTemplate<Integer, Message> template;

    public Activity savePlan(Activity activity)throws Exception{
        //数据库保存方案
        mapper.savePlan(activity);
        //通过kafka推送
        /*
          推送
         */
        Message message = new Message();
        message.setMsgCode(CODE.PUSH_ACTIVITY);
        message.setObj(activity);
        //设置要发送到哪个具体的topic
        template.setDefaultTopic(StaticConfigs.TOPIC_PUSH_ACTIVITY);
        template.sendDefault(activity.getActivityId(), message);
        template.flush();
        return activity;
    }
}
