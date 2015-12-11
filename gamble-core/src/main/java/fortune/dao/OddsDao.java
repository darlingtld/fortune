package fortune.dao;

import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryOdds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    public List<LotteryOdds> getOdds4LotteryIssue(int lotteryIssue, String groupId) {
        Query query = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("groupId").is(groupId)));
        return mongoTemplate.find(query, LotteryOdds.class);
    }

    public LotteryOdds getOdds4LotteryIssue(int lotteryIssue, String groupId, int number) {
        Query query = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("lotteryBallNumber").is(number)));
        return mongoTemplate.findOne(query, LotteryOdds.class);
    }

    public LotteryOdds getOdds(int lotteryIssue, String groupId, int number, LotteryMarkSixType type) {
        Query query = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("lotteryBallNumber").is(number), Criteria.where("lotteryMarkSixType").is(type)));
        return mongoTemplate.findOne(query, LotteryOdds.class);
    }

    public LotteryOdds getOdds4LotteryIssueByType(int lotteryIssue, String groupId, String lotteryMarkSixType) {
        Query query = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("lotteryMarkSixType").is(lotteryMarkSixType)));
        return mongoTemplate.findOne(query, LotteryOdds.class);
    }

    public List<LotteryOdds> getOdds4LotteryIssue(int issue) {
        Query query = new Query(Criteria.where("lotteryIssue").is(issue));
        return mongoTemplate.find(query, LotteryOdds.class);
    }
}
