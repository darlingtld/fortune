package fortune.dao;

import fortune.pojo.LotteryMarkSixWager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tangl9 on 2015-10-21.
 */

@Repository
public class WagerDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveLotteryMarkSixWager(LotteryMarkSixWager lotteryMarkSixWager) {
        mongoTemplate.save(lotteryMarkSixWager);
    }

    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(int userId, int lotteryIssue) {
        Query searchWagerQuery = new Query(Criteria.where("userId").is(userId).andOperator(Criteria.where("lotteryIssue").is(lotteryIssue)));
        return mongoTemplate.find(searchWagerQuery, LotteryMarkSixWager.class);
    }

    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(int userId) {
        Query searchWagerQuery = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(searchWagerQuery, LotteryMarkSixWager.class);
    }

    public void deleteLotteryMarkSixWager(String lotteryMarkSixWagerId) {
        Query deleteQuery = new Query(Criteria.where("id").is(lotteryMarkSixWagerId));
        mongoTemplate.remove(deleteQuery, LotteryMarkSixWager.class);
    }
}
