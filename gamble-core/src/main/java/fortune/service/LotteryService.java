package fortune.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.Utils;
import fortune.dao.LotteryDao;
import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.NextLotteryMarkSixInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.EnumSet;
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
        Utils.logger.info("get all lottery mark six");
        return lotteryDao.getLotteryMarkSixAll();
    }

    @Transactional
    public LotteryMarkSix getLotteryMarkSix(int lotteryIssue) {
        Utils.logger.info("get lottery mark six of issue {}", lotteryIssue);
        return lotteryDao.getLotteryMarkSix(lotteryIssue);
    }

    @Transactional
    public void saveLotteryMarkSix(LotteryMarkSix lotteryMarkSix) {
        Utils.logger.info("save lottery mark six");
        lotteryDao.saveLotteryMarkSix(lotteryMarkSix);
    }

    public JSONArray getLotteryMarkSixType() {
        Utils.logger.info("get lottery mark six type");
        JSONArray jsonArray = new JSONArray();
        EnumSet<LotteryMarkSixType> currEnumSet = EnumSet.allOf(LotteryMarkSixType.class);
        for (LotteryMarkSixType type : currEnumSet) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", type.toString());
            jsonObject.put("value", type.getType());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Transactional
    public int getLatestLotteryIssue() {
        Utils.logger.info("get latest lottery issue");
        return lotteryDao.getLatestLotteryIssue();
    }

    @Transactional
    public List<LotteryMarkSix> getLotteryMarkSixByPagination(int from, int count) {
        Utils.logger.info("get paginated lottery mark six from {} count {}", from, count);
        return lotteryDao.getLotteryMarkSixByPagination(from, count);
    }

    @Transactional
    public NextLotteryMarkSixInfo getNextLotteryMarkSixInfo() {
//        TODO
        Utils.logger.info("get next lottery mark six info");
        NextLotteryMarkSixInfo nextLotteryMarkSixInfo = new NextLotteryMarkSixInfo();
        nextLotteryMarkSixInfo.setIssue(getLatestLotteryIssue() + 1);
        nextLotteryMarkSixInfo.setDate(new Date());
        return nextLotteryMarkSixInfo;
    }
}