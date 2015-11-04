package fortune.service;

import common.Utils;
import fortune.dao.StatDao;
import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangl9 on 2015-11-03.
 */
@Service
public class StatService {
    @Autowired
    private StatDao statDao;

    @Transactional
    public List<LotteryMarkSixStat> getLotteryMarkSixStat(String groupid, int from, int count) {
        Utils.logger.info("get lottery mark six stat for group id {} from {}, count {}", groupid, from, count);
        return statDao.getLotteryMarkSixStat(groupid, from, count);
    }

    @Transactional
    public void saveLotteryMarkSixStat(LotteryMarkSixStat stat) {
        Utils.logger.info("save lottery mark six", stat);
        statDao.saveLotteryMarkSixStat(stat);
    }
}
