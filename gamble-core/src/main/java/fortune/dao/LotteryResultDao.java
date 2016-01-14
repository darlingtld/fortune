package fortune.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import fortune.pojo.LotteryMarkSixWager;
import fortune.pojo.LotteryResult;

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

    public List<LotteryResult> getLotteryResult4LotteryIssue(int lotteryIssue, String groupId) {
        Query query = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("groupId").is(groupId)));
        return mongoTemplate.find(query, LotteryResult.class);
    }

    public LotteryResult getLotteryResult4LotteryIssue(int lotteryIssue, String groupId, String userId) {
        Query query = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("groupId").is(groupId), Criteria.where("userId").is(userId)));
        return mongoTemplate.findOne(query, LotteryResult.class);
    }

    public List<LotteryResult> getLotteryResult4LotteryIssueAndUser(int lotteryIssue, String userId) {
        Query query = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("userId").is(userId)));
        return mongoTemplate.find(query, LotteryResult.class);
    }

    public List<LotteryResult> getLotteryResult4User(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, LotteryResult.class);
    }
    
    /* ------ Calculate  -------*/
    
    public LotteryResult calSumResult4WagerList(List<LotteryMarkSixWager> wagerList) {
        List<String> wagerIdList = new ArrayList<>();
        wagerList.stream().forEach(wager -> {
            wagerIdList.add(wager.getId());
        });
        
        Aggregation agg = newAggregation(
                match(Criteria.where("lotteryMarkSixWagerId").in(wagerIdList)),         //Bug exists in Spring 4.1.6! now use Spring 4.1.8
                group().sum("tuishui").as("tuishui").sum("winningMoney").as("winningMoney")
            );
        AggregationResults<LotteryResult> aggResult = mongoTemplate.aggregate(agg, LotteryResult.class, LotteryResult.class);
        return aggResult.getUniqueMappedResult();
    }

}
