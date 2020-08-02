package com.easyarch.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Activity {
    private int activityId;
    private String activityType;
    private String activityName;
    private String activityTitle;
    private String activityMessage;
    private String activityDescribe;
    private int provider;
    private int sender;
    private Date createTime;
    private Date onlineTime;
    private Date deadline;
    private int activityState;
    private String url;
}
