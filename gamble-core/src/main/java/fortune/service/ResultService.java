package fortune.service;

import common.Utils;
import fortune.dao.LotteryResultDao;
import fortune.pojo.LotteryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangl9 on 2015-10-21.
 */
@Service
public class ResultService {

    @Autowired
    private LotteryResultDao lotteryResultDao;

    @Transactional
    public List<LotteryResult> getLotteryResult4LotteryIssueAndUser(int lotteryIssue, String userId) {
        Utils.logger.info("get lottery result of lottery issue {}, user id {}", lotteryIssue, userId);
        return lotteryResultDao.getLotteryResult4LotteryIssueAndUser(lotteryIssue, userId);
    }

    @Transactional
    public LotteryResult getLotteryResultById(int lotteryResultId) {
        Utils.logger.info("get lottery result by id {}", lotteryResultId);
        return lotteryResultDao.getLotteryResultById(lotteryResultId);
    }

    @Transactional
    public void saveLotteryResult(LotteryResult lotteryResult) {
        Utils.logger.info("save lottery result {}", lotteryResult);
        lotteryResultDao.saveLotteryResult(lotteryResult);
    }

    @Transactional
    public List<LotteryResult> getAll() {
        Utils.logger.info("get all lottery results");
        return lotteryResultDao.getAll();
    }

    @Transactional
    public List<LotteryResult> getLotteryResult4LotteryIssue(int lotteryIssue, String groupId) {
        Utils.logger.info("get lottery result of lottery issue {}, group id {}", lotteryIssue, groupId);
        return lotteryResultDao.getLotteryResult4LotteryIssue(lotteryIssue, groupId);
    }

    @Transactional
    public LotteryResult getLotteryResult4LotteryIssue(int lotteryIssue, String groupId, String userId) {
        Utils.logger.info("get lottery result of lottery issue {}, group id {}, user id {}", lotteryIssue, groupId, userId);
        return lotteryResultDao.getLotteryResult4LotteryIssue(lotteryIssue, groupId, userId);
    }

    @Transactional
    public List<LotteryResult> getLotteryResult4User(String userId) {
        Utils.logger.info("get lottery result of user id {}", userId);
        return lotteryResultDao.getLotteryResult4User(userId);
    }
}
