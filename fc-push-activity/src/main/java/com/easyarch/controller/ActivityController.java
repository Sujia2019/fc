package com.easyarch.controller;

import com.easyarch.entity.Activity;
import com.easyarch.entity.Response;
import com.easyarch.service.imp.ActivityServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {
    private static final Logger log = LoggerFactory.getLogger(ActivityController.class);
    @Autowired
    ActivityServiceImp activityService;

    @RequestMapping("/savePlan")
    @ResponseBody
    public Response savePlan(@RequestParam Activity activity){
        Response response = new Response();
        try{
            activity = activityService.savePlan(activity);
            response.setData("保存方案成功！方案id为:"+activity.getActivityId());
            log.info("保存策划成功: 方案id为【{}】",activity.getActivityId());
        }catch (Exception e){
            response.setCode("error");
            response.setErrorMsg("保存策划方案时出错:"+e.getMessage());
            log.error("保存策划方案时出错: {}",e.getMessage());
        }
        return response;
    }
}
