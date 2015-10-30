package fortune.dao;

import fortune.pojo.PGroup;
import fortune.pojo.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-13.
 */
@Repository
public class PGroupDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public PGroup getGroupById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, PGroup.class);
    }

    public void createGroup(PGroup pGroup) {
        mongoTemplate.save(pGroup);
    }

    public List<PGroup> getGroupAll() {
        return mongoTemplate.findAll(PGroup.class);
    }

    public PGroup updatePGroup(PGroup pGroup) {
        Query query = new Query(Criteria.where("id").is(pGroup.getId()));
        Update update = new Update();
        update.set("name", pGroup.getName());
        update.set("subPGroupList", pGroup.getSubPGroupList());
        update.set("userList", pGroup.getUserList());
        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), PGroup.class);
    }
}
