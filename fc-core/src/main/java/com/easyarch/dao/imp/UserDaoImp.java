package com.easyarch.dao.imp;


import com.easyarch.dao.UserDao;
import com.easyarch.model.PlayerInfo;
import com.easyarch.model.UserInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserDaoImp implements UserDao {

    private SqlSession sqlSession;

    @Autowired
    private void setSqlSession(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }

    public UserDaoImp(){

    }

    /*
    @Before
     */
    @Override
    public int searchById(String id) {
        return sqlSession.selectOne("searchById",DigestUtils.md5Hex(id));
    }

    /*
    @Before
     */
    @Override
    public int searchByName(String name) {
        return sqlSession.selectOne("searchByName",name);
    }

    /*
    @Before
     */
    @Override
    public UserInfo searchUserById(String id) {
        return sqlSession.selectOne("searchUserById",DigestUtils.md5Hex(id));
    }

    /*
    @After
     */
    @Override
    public int insertUser(UserInfo user) {
        int x = sqlSession.insert("insertUser",setMd5Hex(user));
        sqlSession.commit();
        return x;
    }

    @Override
    public UserInfo searchUserByUserInfo(UserInfo user) {
        UserInfo userInfo = setMd5Hex(user);
        if(sqlSession.selectOne("searchUserByUserInfo",userInfo)!=null)
            return user;
        else{
            return null;
        }
    }

    /*
    @After
     */
    @Override
    public int updatePlayer(PlayerInfo player) {
        int x = sqlSession.update("updatePlayer",player);
        sqlSession.commit();
        return x;
    }

    /*
    切点
    @Before
    */
    public PlayerInfo getPlayer(String userId){
        System.out.println(userId);
        return sqlSession.selectOne("getPlayer",userId);
    }

    /*
    切点@After
     */
    public int insertPlayer(PlayerInfo player){
        int x = sqlSession.insert("insertPlayer",player);
        sqlSession.commit();
        return x;
    }

    private UserInfo setMd5Hex(UserInfo user){
        UserInfo userInfo = new UserInfo();
        String pwd = DigestUtils.md5Hex(user.getUserPwd());
        String id = DigestUtils.md5Hex(user.getUserId());
        userInfo.setUserId(id);
        userInfo.setUserPwd(pwd);
        return userInfo;
    }
}
