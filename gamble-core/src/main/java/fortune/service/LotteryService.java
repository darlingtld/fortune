package fortune.service;

import common.Utils;
import fortune.dao.LotteryDao;
import fortune.pojo.LotteryMarkSix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangl9 on 2015-10-14.
 */
@Service
public class LotteryService {

    @Autowired
    private LotteryDao lotteryDao;

    @Transactional
    public List<LotteryMarkSix> getLotteryMarkSixAll() {
        Utils.logger.info("get all fortune mark six");
        return lotteryDao.getLotteryMarkSixAll();
    }

    @Transactional
    public void saveLotteryMarkSix(LotteryMarkSix lotteryMarkSix) {
        Utils.logger.info("save fortune mark six");
        lotteryDao.saveLotteryMarkSix(lotteryMarkSix);
    }
}
