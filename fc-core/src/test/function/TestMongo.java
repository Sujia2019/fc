package function;

import com.easyarch.dao.mapper.FightRecord;
import com.easyarch.dao.mapper.UserAll;
import com.easyarch.dao.mapper.UserAllDoc;
import com.easyarch.dao.mapper.UserSetting;
import com.easyarch.utils.MongoUtil;
import com.easyarch.utils.config.DataConfig;
import com.easyarch.utils.config.NettyConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

/*
    private String userId;
    private String userpwd;
    private List<String> groupIds;
    private List<FightRecord> fightRecord;
    private List<String> friends;
    private UserSetting setting;
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class})
public class TestMongo {
    @Autowired
    UserAllDoc userAllDoc;

    /*
    测试时请把NettyServer上面的注解注释掉
     */
    @Test
    public void createUserDoc(){
        UserAll userDoc = new UserAll();
        userDoc.setUserId("18539403150");
        userDoc.setUserpwd("123456");
        ArrayList<String> groups = new ArrayList<>();
        groups.add("111");
        userDoc.setGroupIds(groups);
        ArrayList<String> friends = new ArrayList<>();
        userDoc.setFriends(friends);
        ArrayList<FightRecord> records = new ArrayList<>();
        userDoc.setFightRecord(records);
        UserSetting setting = new UserSetting();
        setting.setAcceptFriendRequest(true);
        setting.setAcceptMessage(true);
        userDoc.setSetting(setting);

        userAllDoc.insertUserDoc(userDoc);
//        mongoUtil.getMongoTemplate().insert(userDoc);
    }

    @Test
    public void getUserDoc(){
        String userId = "184500237";
        UserAll doc = userAllDoc.getUserDoc(userId);
        System.out.println(doc);
    }
}
