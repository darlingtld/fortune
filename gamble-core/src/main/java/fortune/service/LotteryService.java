package fortune.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.Utils;
import fortune.dao.LotteryDao;
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