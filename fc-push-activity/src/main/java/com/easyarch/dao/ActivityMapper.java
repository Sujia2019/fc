package com.easyarch.dao;

import com.easyarch.entity.Activity;
import com.easyarch.service.imp.ActivityServiceImp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityMapper {

    @Options(useGeneratedKeys=true, keyProperty="activityId", keyColumn="activity_id")
    @Insert("insert into activity(" +
            "activity_type," +
            "activity_name,"+
            "activity_title,"+
            "activity_message,"+
            "activity_describe,"+
            "provider,"+
            "sender,"+
            "create_time,"+
            "online_time,"+
            "deadline,"+
            "activity_state,"+
            "url"+
            ")value(#{activityType},#{activityName}," +
            "#{activityTitle}," +
            "#{activity_message}" +
            "#{activity_describe}," +
            "#{provider}," +
            "#{sender},"+
            "#{createTime}," +
            "#{onlineTime}," +
            "#{deadline}," +
            "#{activityState}," +
            "#{url})")
    void savePlan(Activity activity)throws Exception;

}
