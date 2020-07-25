package com.easyarch.service.imp;

import com.easyarch.dao.ActivityMapper;
import com.easyarch.entity.Activity;
import com.easyarch.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImp implements ActivityService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImp.class);
    @Autowired
    ActivityMapper mapper;

    public Activity savePlan(Activity activity)throws Exception{
        //数据库保存方案
        mapper.savePlan(activity);
        //通过kafka推送
        /*
          推送
         */
        return activity;
    }
}
