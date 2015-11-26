package fortune.service;

import common.Utils;
import fortune.dao.OddsDao;
import fortune.pojo.LotteryOdds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangl9 on 2015-10-16.
 */
@Service
public class OddsService {

    @Autowired
    private OddsDao oddsDao;

    @Transactional
    public LotteryOdds getOddsById(String oddsId) {
        Utils.logger.info("get odds by id {}", oddsId);
        return oddsDao.getOddsById(oddsId);
    }

    @Transactional
    public void saveOdds(LotteryOdds odds) {
        Utils.logger.info("save odds");
        oddsDao.saveOdds(odds);
    }

    @Transactional
    public List<LotteryOdds> getAll() {
        Utils.logger.info("get all lottery odds");
        return oddsDao.getAll();
    }

    @Transactional
    public List<LotteryOdds> getOdds4LotteryIssue(int lotteryIssue, String groupId) {
        Utils.logger.info("get odds for lottery issue {} of group id {}", lotteryIssue, groupId);
        return oddsDao.getOdds4LotteryIssue(lotteryIssue, groupId);
    }

    @Transactional
    public LotteryOdds getOdds4LotteryIssue(int lotteryIssue, String groupId, int number) {
        Utils.logger.info("get odds for lottery issue {} of group id {} of ball {}", lotteryIssue, groupId, number);
        return oddsDao.getOdds4LotteryIssue(lotteryIssue, groupId, number);
    }

    @Transactional
    public LotteryOdds getOdds4LotteryIssueByType(int lotteryIssue, String groupId, String lotteryMarkSixType) {
        Utils.logger.info("get odds for lottery issue {} of group id {} of type {}", lotteryIssue, groupId, lotteryMarkSixType);
        return oddsDao.getOdds4LotteryIssueByType(lotteryIssue, groupId, lotteryMarkSixType);
    }

    @Transactional
    public List<LotteryOdds> getOdds4LotteryIssue(int issue) {
        Utils.logger.info("get odds for lottery issue {}", issue);
        return oddsDao.getOdds4LotteryIssue(issue);
    }
}
