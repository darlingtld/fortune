package fortune.service;

import common.Utils;
import fortune.dao.WagerDao;
import fortune.pojo.LotteryMarkSixWager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangl9 on 2015-10-21.
 */
@Service
public class WagerService {

    @Autowired
    private WagerDao wagerDao;

    @Transactional
    public void saveLotteryMarkSixWager(LotteryMarkSixWager lotteryMarkSixWager) {
        Utils.logger.info("save lottery mark six wager {}", lotteryMarkSixWager);
        wagerDao.saveLotteryMarkSixWager(lotteryMarkSixWager);
    }

    @Transactional
    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(int userId, int lotteryIssue) {
        Utils.logger.info("get lottery mark six wager list of user {}, issue{}", userId, lotteryIssue);
        return wagerDao.getLotteryMarkSixWagerList(userId, lotteryIssue);
    }

    @Transactional
    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(int userId) {
        Utils.logger.info("get all lottery mark six wager list of user {}", userId);
        return wagerDao.getLotteryMarkSixWagerList(userId);
    }

    @Transactional
    public void deleteLotteryMarkSixWager(String lotteryMarkSixWagerId) {
        Utils.logger.info("delete lottery mark six wager id {}", lotteryMarkSixWagerId);
        wagerDao.deleteLotteryMarkSixWager(lotteryMarkSixWagerId);
    }
}
