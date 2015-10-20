package fortune.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.Utils;
import fortune.dao.LotteryDao;
import fortune.pojo.GambleBetLotteryMarkSix;
import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void saveLotteryMarkSix(LotteryMarkSix lotteryMarkSix) {
        Utils.logger.info("save lottery mark six");
        lotteryDao.saveLotteryMarkSix(lotteryMarkSix);
    }

    @Transactional
    public void saveGambleBetLotteryMarkSix(GambleBetLotteryMarkSix gambleBetLotteryMarkSix) {
        Utils.logger.info("save gamble bet lottery mark six");
        lotteryDao.saveGambleBetLotteryMarkSix(gambleBetLotteryMarkSix);
    }

    @Transactional
    public List<GambleBetLotteryMarkSix> getGambleBetLotteryMarkSixList(int userId) {
        Utils.logger.info("get gamble bet record for user {}", userId);
        return lotteryDao.getGambleBetLotteryMarkSixList(userId);
    }

    @Transactional
    public void deleteGambleBetLotteryMarkSix(int gambleBetLotteryMarkSixId) {
        Utils.logger.info("delete gamble bet lottery mark six by id {}", gambleBetLotteryMarkSixId);
        lotteryDao.deleteGambleBetLotteryMarkSix(gambleBetLotteryMarkSixId);
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
}