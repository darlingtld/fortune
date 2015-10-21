package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fortune.pojo.*;
import fortune.service.LotteryService;
import fortune.service.OddsService;
import fortune.service.PGroupService;
import fortune.service.UserService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

/**
 * Created by tangl9 on 2015-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class LotteryServiceTest {

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private UserService userService;

    @Autowired
    private PGroupService pGroupService;

    @Test
    public void saveLottery() {
        List<MarkSixColor> markSixColorList = Arrays.asList(MarkSixColor.RED, MarkSixColor.BLUE, MarkSixColor.GREEN);
        LotteryMarkSix lotteryMarkSix = new LotteryMarkSix();
        lotteryMarkSix.setOne(new Random().nextInt(49) + 1);
        lotteryMarkSix.setTwo(new Random().nextInt(49) + 1);
        lotteryMarkSix.setThree(new Random().nextInt(49) + 1);
        lotteryMarkSix.setFour(new Random().nextInt(49) + 1);
        lotteryMarkSix.setFive(new Random().nextInt(49) + 1);
        lotteryMarkSix.setSix(new Random().nextInt(49) + 1);
        lotteryMarkSix.setSpecial(new Random().nextInt(49) + 1);

        lotteryMarkSix.setIssue((int) (System.currentTimeMillis() / 1000000));

        lotteryMarkSix.setTimestamp(new Date());

        System.out.println(lotteryMarkSix);
        lotteryService.saveLotteryMarkSix(lotteryMarkSix);
    }

    @Test
    public void getLotteryMarkSixType() {
        JSONArray jsonArray = new JSONArray();
        EnumSet<LotteryMarkSixType> currEnumSet = EnumSet.allOf(LotteryMarkSixType.class);
        for (LotteryMarkSixType type : currEnumSet) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", type.toString());
            jsonObject.put("value", type.getType());
            jsonArray.add(jsonObject);
        }
        System.out.println(jsonArray);
    }
}
