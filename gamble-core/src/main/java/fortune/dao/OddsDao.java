package fortune.dao;

import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryOdds;
import fortune.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tangl9 on 2015-10-16.
 */
@Repository
public class OddsDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public LotteryOdds getOddsById(String oddsId) {
        Query query = new Query(Criteria.where("id").is(oddsId));
        return mongoTemplate.findOne(query, LotteryOdds.class);
    }

    public void saveOdds(LotteryOdds odds) {
        mongoTemplate.save(odds);
    }

    public List<LotteryOdds> getAll() {
        return mongoTemplate.findAll(LotteryOdds.class);
    }

    public List<LotteryOdds> getOdds4LotteryIssue(int lotteryIssue, String groupId, String panlei) {
        Query query = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("groupId").is(groupId),Criteria.where("panlei").is(panlei)));
        return mongoTemplate.find(query, LotteryOdds.class);
    }

    public LotteryOdds getOdds4LotteryIssue(int lotteryIssue, String groupId, int number, String panlei) {
        Query query = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("lotteryBallNumber").is(number), Criteria.where("panlei").is(panlei)));
        return mongoTemplate.findOne(query, LotteryOdds.class);
    }

    public LotteryOdds getOdds(int lotteryIssue, String groupId, int number, LotteryMarkSixType type,String panlei) {
        Query query = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("lotteryBallNumber").is(number), Criteria.where("lotteryMarkSixType").is(type),Criteria.where("panlei").is(panlei)));
        return mongoTemplate.findOne(query, LotteryOdds.class);
    }

    public List<LotteryOdds> getOdds4LotteryIssueByType(int lotteryIssue, String groupId, String lotteryMarkSixType, String panlei) {
        Query query = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("lotteryMarkSixType").is(lotteryMarkSixType), Criteria.where("panlei").is(panlei)));
        return mongoTemplate.find(query, LotteryOdds.class);
    }

    public List<LotteryOdds> getOdds4LotteryIssue(int issue, String panlei) {
        Query query = new Query(Criteria.where("lotteryIssue").is(issue).andOperator(Criteria.where("panlei").is(panlei)));
        return mongoTemplate.find(query, LotteryOdds.class);
    }

    public LotteryOdds changeOdds(String oddsId, double odds) {
        Query query = new Query(Criteria.where("id").is(oddsId));
        Update update = new Update();
        update.set("odds", odds);
        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), LotteryOdds.class);
    }
}
