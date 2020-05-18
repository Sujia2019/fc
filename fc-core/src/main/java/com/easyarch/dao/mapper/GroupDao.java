package com.easyarch.dao.mapper;
import com.easyarch.utils.MongoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;


@Repository
public class GroupDao {
//    @Resource
//    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoUtil mongoUtil;

    //插入,创建一条记录
    public void createGroup(GroupMsg group){
        mongoUtil.getMongoTemplate().insert(group);
    }

    //添加新成员
    public void insertMember(String groupId,String member){
        Query query=new Query();
        query.addCriteria(Criteria.where("groupId").is(groupId));
        Update update = new Update();
        update.addToSet("membersId", member);
        mongoUtil.getMongoTemplate().upsert(query, update, GroupMsg.class);
    }

    //添加新成员
    public void insertMembers(String groupId,String[] members){
        Query query=new Query();
        query.addCriteria(Criteria.where("groupId").is(groupId));
        Update update = new Update();
        for(String s : members){
            update.addToSet("membersId", s);
            mongoUtil.getMongoTemplate().upsert(query, update, GroupMsg.class);
        }
    }
    //移出该成员
    public void delMember(String groupId,String member){
        Query query = Query.query(Criteria.where("groupId").is(groupId));
        Update update = new Update();
        update.pull("membersId",member);
        mongoUtil.getMongoTemplate().updateFirst(query, update, GroupMsg.class);
    }

    //创建管理员
    public void insertManager(String groupId,String memberId){
        Query query=new Query();
        query.addCriteria(Criteria.where("groupId").is(groupId));
        Update update = new Update();
        update.addToSet("managers", memberId);
        mongoUtil.getMongoTemplate().upsert(query, update, GroupMsg.class);
    }
    //删除管理员
    public void delManager(String groupId,String memberId){
        Query query = Query.query(Criteria.where("groupId").is(groupId));
        Update update = new Update();
        update.pull("managers",memberId);
        mongoUtil.getMongoTemplate().updateFirst(query, update, GroupMsg.class);
    }

    //删除群组
    public void deleteGroup(String groupId){
        Query query=new Query(Criteria.where("groupId").is(groupId));
        mongoUtil.getMongoTemplate().remove(query,GroupMsg.class,"group").getDeletedCount();
    }

    //查询组
    public GroupMsg searchGroup(String groupId){
        Query query = new Query(Criteria.where("groupId").is(groupId));
        return mongoUtil.getMongoTemplate().findOne(query, GroupMsg.class);
    }

    //查询组
    public GroupMsg searchGroupByName(String groupName){
        Query query = new Query(Criteria.where("groupName").is(groupName));
        return mongoUtil.getMongoTemplate().findOne(query, GroupMsg.class);
    }

    //查询当前用户的所有组
    public List<GroupMsg> searchGroups(String userId){
        Query query = new Query(Criteria.where("userId").is(userId));
        UserAll all = mongoUtil.getMongoTemplate().findOne(query,UserAll.class);
        if(all!=null){
            List<String> groups = all.getGroupIds();
            if(groups.isEmpty()){
                return null;
            }
            List<GroupMsg> groupMsgList = new ArrayList<>();
            for(String group : groups){
                Query q = new Query(Criteria.where("groupId").is(group));
                GroupMsg groupMsg = mongoUtil.getMongoTemplate().findOne(q,GroupMsg.class);
                if (groupMsg!=null){
                    groupMsgList.add(groupMsg);
                }
            }
            return groupMsgList;
        }
        return null;
    }

}
