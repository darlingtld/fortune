package fortune.service;

import common.Utils;
import fortune.dao.StatDao;
import fortune.pojo.LotteryMarkSixUserStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by tangl9 on 2015-11-23.
 */
@Service
public class StatService {

    @Autowired
    private StatDao statDao;

    @Transactional
    public List<LotteryMarkSixUserStat> getLotteryMarkSixUserStatList(String userId, Date startDate, Date endDate) {
        Utils.logger.info("get lottery mark six user stat list of user id {}, from {} to {}", userId, startDate, endDate);
        return statDao.getLotteryMarkSixUserStatList(userId, startDate, endDate);
    }

    @Transactional
    public void saveLotteryMarkSixStat(LotteryMarkSixUserStat stat) {
        Utils.logger.info("save lottery mark six user stat", stat);
        statDao.saveLotteryMarkSixStat(stat);
    }
}
