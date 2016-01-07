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
        mongoTemplate.remove(new Query(), "lottery_tuishui");
    }

    public List<LotteryTuishui> getTuishui4User(String userId, String groupId, String panlei) {
        Query query = new Query(Criteria.where("userId").is(userId).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("panlei").is(panlei)));
        return mongoTemplate.find(query, LotteryTuishui.class);
    }

    public LotteryTuishui getTuishui4LotteryIssue(String userId, String groupId, int number, String panlei) {
        Query query = new Query(Criteria.where("userId").is(userId).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("lotteryBallNumber").is(number), Criteria.where("panlei").is(panlei)));
        return mongoTemplate.findOne(query, LotteryTuishui.class);
    }

    public LotteryTuishui getTuishui(String userId, String groupId, int number, LotteryMarkSixType type, LotteryMarkSixType ballType, String panlei) {
        Criteria criteria = null;
        if (ballType != null) {
            criteria = Criteria.where("userId").is(userId).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("lotteryBallNumber").is(number), Criteria.where("lotteryMarkSixType").is(type), Criteria.where("panlei").is(panlei), Criteria.where("lotteryBallType").is(ballType));
        } else {
            criteria = Criteria.where("userId").is(userId).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("lotteryBallNumber").is(number), Criteria.where("lotteryMarkSixType").is(type), Criteria.where("panlei").is(panlei));
        }
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, LotteryTuishui.class);
    }

    public LotteryTuishui getTuishui4LotteryIssueByBallType(String userId, String groupId, String lotteryMarkSixType, String panlei, String ballType) {
        Query query = new Query(Criteria.where("userId").is(userId).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("lotteryMarkSixType").is(lotteryMarkSixType), Criteria.where("panlei").is(panlei), Criteria.where("lotteryBallType").is(ballType)));
        return mongoTemplate.findOne(query, LotteryTuishui.class);
    }

    public List<LotteryTuishui> getTuishui4LotteryIssueByType(String userId, String groupId, String lotteryMarkSixType, String panlei) {
        Query query = new Query(Criteria.where("userId").is(userId).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("lotteryMarkSixType").is(lotteryMarkSixType), Criteria.where("panlei").is(panlei)));
        return mongoTemplate.find(query, LotteryTuishui.class);
    }

    public List<LotteryTuishui> getTuishui4LotteryIssue(int issue, String panlei) {
        Query query = new Query(Criteria.where("userId").is(issue).andOperator(Criteria.where("panlei").is(panlei)));
        return mongoTemplate.find(query, LotteryTuishui.class);
    }

    public LotteryTuishui changeTuishui(String tuishuiId, double tuishui) {
        Query query = new Query(Criteria.where("id").is(tuishuiId));
        Update update = new Update();
        update.set("tuishui", tuishui);
        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), LotteryTuishui.class);
    }
}
