package com.easyarch.serviceImp;

import com.easyarch.model.UserInfo;

public interface UserService {
    public UserInfo login(UserInfo user);

    public boolean regist(UserInfo user);
}
