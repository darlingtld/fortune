package fortune.dao;

import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryMarkSixWager;
import fortune.pojo.LotteryMarkSixWagerStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
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

    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(String userId, int lotteryIssue) {
        Query searchWagerQuery = new Query(Criteria.where("userId").is(userId).andOperator(Criteria.where("lotteryIssue").is(lotteryIssue)));
        return mongoTemplate.find(searchWagerQuery, LotteryMarkSixWager.class);
    }

    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(String userId, String groupId) {
        Query searchWagerQuery = new Query(Criteria.where("userId").is(userId).andOperator(Criteria.where("pgroupId").is(groupId)));
        searchWagerQuery.with(new Sort(Sort.Direction.DESC, "timestamp"));
        return mongoTemplate.find(searchWagerQuery, LotteryMarkSixWager.class);
    }

    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(String userId) {
        Query searchWagerQuery = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(searchWagerQuery, LotteryMarkSixWager.class);
    }

    public void deleteLotteryMarkSixWager(String lotteryMarkSixWagerId) {
        Query deleteQuery = new Query(Criteria.where("id").is(lotteryMarkSixWagerId));
        mongoTemplate.remove(deleteQuery, LotteryMarkSixWager.class);
    }

    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(String userId, String pgroupId, int lotteryIssue) {
        Query searchWagerQuery = new Query(Criteria.where("userId").is(userId).andOperator(Criteria.where("lotteryIssue").is(lotteryIssue), Criteria.where("pgroupId").is(pgroupId)));
        return mongoTemplate.find(searchWagerQuery, LotteryMarkSixWager.class);
    }

    public LotteryMarkSixWager updateLotteryMarkSixWager(LotteryMarkSixWager lotteryMarkSixWager) {
        Query searchWagerQuery = new Query(Criteria.where("id").is(lotteryMarkSixWager.getId()));
        Update update = new Update();
        update.set("timestamp", new Date());
        update.set("lotteryMarkSixWagerStubList", lotteryMarkSixWager.getLotteryMarkSixWagerStubList());
        update.set("totalStakes", lotteryMarkSixWager.getTotalStakes());
        update.set("lotteryMarkSixType", lotteryMarkSixWager.getLotteryMarkSixType());
        return mongoTemplate.findAndModify(searchWagerQuery, update, new FindAndModifyOptions().returnNew(true), LotteryMarkSixWager.class);
    }

    public LotteryMarkSixWager getLotteryMarkSixWager(String lotteryMarkSixWagerId) {
        Query searchWagerQuery = new Query(Criteria.where("id").is(lotteryMarkSixWagerId));
        return mongoTemplate.findOne(searchWagerQuery, LotteryMarkSixWager.class);
    }

    public List<LotteryMarkSixWager> getAll(int lotteryIssue) {
        Query searchWagerQuery = new Query(Criteria.where("lotteryIssue").is(lotteryIssue));
        return mongoTemplate.find(searchWagerQuery, LotteryMarkSixWager.class);
    }

    public List<LotteryMarkSixWager> getLotteryMarkSixWagerListByType(int lotteryIssue, LotteryMarkSixType lotteryMarkSixType) {
        Query searchWagerQuery = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("lotteryMarkSixType").is(lotteryMarkSixType)));
        return mongoTemplate.find(searchWagerQuery, LotteryMarkSixWager.class);
    }

    public List<LotteryMarkSixWager> getLotteryMarkSixWagerListOfGroup(String groupid, String panlei, int lotteryIssue) {
        Query searchWagerQuery = new Query(Criteria.where("lotteryIssue").is(lotteryIssue).andOperator(Criteria.where("pgroupId").is(groupid), Criteria.where("panlei").is(panlei)));
        return mongoTemplate.find(searchWagerQuery, LotteryMarkSixWager.class);
    }

    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(LotteryMarkSixType type, String groupId, String panlei, int issue, int number) {
        Query searchWagerQuery = new Query(Criteria.where("lotteryIssue").is(issue).andOperator(Criteria.where("pgroupId").is(groupId), Criteria.where("lotteryMarkSixType").is(type), Criteria.where("panlei").is(panlei)));
        List<LotteryMarkSixWager> tmpwagerList = mongoTemplate.find(searchWagerQuery, LotteryMarkSixWager.class);
        List<LotteryMarkSixWager> wagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : tmpwagerList) {
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if (stub.getNumber() == number) {
                    wagerList.add(wager);
                    break;
                }
            }
        }
        return wagerList;
    }
    
    public List<LotteryMarkSixWager> getWagerListByIssues(String groupId, List<Integer> issueList) {
        Query searchWagerQuery = new Query(Criteria.where("lotteryIssue").in(issueList).andOperator(Criteria.where("pgroupId").is(groupId)));
        return mongoTemplate.find(searchWagerQuery, LotteryMarkSixWager.class);
    }

    //TODO code can be reused in getLotteryMarkSixWagerList()
    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(LotteryMarkSixType type, String groupId, String panlei, int issue, LotteryMarkSixType ballType) {
        Query searchWagerQuery = null;
        
        if ("ALL".equalsIgnoreCase(panlei)) {
            searchWagerQuery = new Query(Criteria.where("lotteryIssue").is(issue).andOperator(Criteria.where("pgroupId").is(groupId), Criteria.where("lotteryMarkSixType").is(type)));
        } else {
            searchWagerQuery = new Query(Criteria.where("lotteryIssue").is(issue).andOperator(Criteria.where("pgroupId").is(groupId), Criteria.where("lotteryMarkSixType").is(type), Criteria.where("panlei").is(panlei)));
        }

        if (ballType != null) {
            List<LotteryMarkSixWager> wagerList = new ArrayList<>();
            List<LotteryMarkSixWager> tmpwagerList = mongoTemplate.find(searchWagerQuery, LotteryMarkSixWager.class);
            for (LotteryMarkSixWager wager : tmpwagerList) {
                for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                    if (stub.getLotteryMarkSixType().equals(ballType)) {
                        wagerList.add(wager);
                        break;
                    }
                }
            }
            return wagerList;
        } else {
            return mongoTemplate.find(searchWagerQuery, LotteryMarkSixWager.class);
        }
    }

    public void deleteLotteryMarkSixWager(String pGroupId, String userId) {
        Query query = new Query(Criteria.where("pgroupId").is(pGroupId).andOperator(Criteria.where("userId").is(userId)));
        mongoTemplate.findAndRemove(query, LotteryMarkSixWager.class);
    }
}
