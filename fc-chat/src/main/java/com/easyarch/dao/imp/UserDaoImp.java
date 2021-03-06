package com.easyarch.dao.imp;

import com.easyarch.dao.UserDao;
import com.easyarch.model.UserInfo;
import com.easyarch.utils.MybatisConf;
import org.apache.ibatis.session.SqlSession;

public class UserDaoImp implements UserDao {
    private SqlSession session = MybatisConf.getSqlSession();
    @Override
    public int searchById(String id) {
        return session.selectOne("searchById",id);
    }

    @Override
    public int searchByName(String name) {
        return session.selectOne("searchByName",name);
    }

    @Override
    public UserInfo searchUserById(String id) {
        return session.selectOne("searchUserById",id);
    }

    @Override
    public int insertUser(UserInfo user) {
        int x = session.insert("insertUser",user);
        session.commit();
        return x;
    }

    @Override
    public UserInfo searchUserByUserInfo(UserInfo user) {
        return session.selectOne("searchUserByUserInfo",user);
    }
}
