package fortune.dao;

import fortune.pojo.LotteryResult;
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
public class LotteryResultDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public LotteryResult getLotteryResultById(int lotteryResultId) {
        Query query = new Query(Criteria.where("id").is(lotteryResultId));
        return mongoTemplate.findOne(query, LotteryResult.class);
    }

    public void saveLotteryResult(LotteryResult lotteryResult) {
        mongoTemplate.save(lotteryResult);
    }

    public List<LotteryResult> getAll() {
        return mongoTemplate.findAll(LotteryResult.class);
    }

    public List<LotteryResult> getLotteryResult4LotteryIssue(int lotteryIssue, int groupId) {
        Query query = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("groupId").is(groupId)));
        return mongoTemplate.find(query, LotteryResult.class);
    }

    public LotteryResult getLotteryResult4LotteryIssue(int lotteryIssue, int groupId, int userId) {
        Query query = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("userId").is(userId)));
        return mongoTemplate.findOne(query, LotteryResult.class);
    }

    public List<LotteryResult> getLotteryResult4LotteryIssueAndUser(int lotteryIssue, int userId) {
        Query query = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("userId").is(userId)));
        return mongoTemplate.find(query, LotteryResult.class);
    }

    public List<LotteryResult> getLotteryResult4User(int userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, LotteryResult.class);
    }

}
