package fortune.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryTuishui;
import fortune.pojo.PGroup;

/**
 * Created by jason on 2015-01-06.
 */
@Repository
public class TuishuiDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public LotteryTuishui getTuishuiById(String tuishuiId) {
        Query query = new Query(Criteria.where("id").is(tuishuiId));
        return mongoTemplate.findOne(query, LotteryTuishui.class);
    }

    public void saveTuishui(LotteryTuishui tuishui) {
        mongoTemplate.save(tuishui);
    }

    public List<LotteryTuishui> getAll() {
        return mongoTemplate.findAll(LotteryTuishui.class);
    }

    public void removeAll() {
        mongoTemplate.remove(new Query(), LotteryTuishui.class);
    }
    
    public void remove(String userId, String groupId) {
        mongoTemplate.remove(new Query(Criteria.where("userId").is(userId).andOperator(Criteria.where("groupId").is(groupId))), LotteryTuishui.class);
    }

    public List<LotteryTuishui> getTuishui4User(String userId, String groupId, String panlei) {
        Query query = new Query(Criteria.where("userId").is(userId).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("panlei").is(panlei)));
        List<LotteryTuishui> tuishuiList = mongoTemplate.find(query, LotteryTuishui.class);
        return tuishuiList;
    }

    public LotteryTuishui changeTuishui(String tuishuiId, double tuishui) {
        Query query = new Query(Criteria.where("id").is(tuishuiId));
        Update update = new Update();
        update.set("tuishui", tuishui);
        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), LotteryTuishui.class);
    }

    public LotteryTuishui getTuishui4UserOfType(String userId, String groupId, String panlei, LotteryMarkSixType type) {
        Query query = new Query(Criteria.where("userId").is(userId).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("panlei").is(panlei), Criteria.where("lotteryMarkSixType").is(type)));
        return mongoTemplate.findOne(query, LotteryTuishui.class);
    }
}
