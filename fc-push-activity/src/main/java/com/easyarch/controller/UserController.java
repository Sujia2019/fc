package com.easyarch.controller;

import com.easyarch.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/login")
    @ResponseBody
    public User getUser(){
        User user = new User();
        user.setUserId(1);
        user.setUserName("岚贝贝");
        user.setUserAuth(0);
        return user;
    }
}
