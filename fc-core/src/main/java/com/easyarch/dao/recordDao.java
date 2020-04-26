package com.easyarch.dao;


import com.easyarch.model.other.FightRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface recordDao {

    int insertRecord();

    List<FightRecord> searchRecord(String userId);

    int searchRecordCount();

    int searchRecordCountById(String userId);


}
