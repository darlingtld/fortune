package service;

import fortune.pojo.LotteryMarkSix;
import fortune.pojo.MarkSixColor;
import fortune.pojo.Odds;
import fortune.service.LotteryService;
import fortune.service.OddsService;
import fortune.service.PGroupService;
import fortune.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by tangl9 on 2015-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class LotteryServiceTest {

    @Autowired
    private LotteryService lotteryService;

    @Test
    public void saveLottery() {
        List<MarkSixColor> markSixColorList = Arrays.asList(MarkSixColor.RED, MarkSixColor.BLUE, MarkSixColor.GREEN);
        LotteryMarkSix lotteryMarkSix = new LotteryMarkSix();
        lotteryMarkSix.setOne(new Random().nextInt(49) + 1);
        lotteryMarkSix.setOneColor(markSixColorList.get(new Random().nextInt(3)));
        lotteryMarkSix.setTwo(new Random().nextInt(49) + 1);
        lotteryMarkSix.setTwoColor(markSixColorList.get(new Random().nextInt(3)));
        lotteryMarkSix.setThree(new Random().nextInt(49) + 1);
        lotteryMarkSix.setThreeColor(markSixColorList.get(new Random().nextInt(3)));
        lotteryMarkSix.setFour(new Random().nextInt(49) + 1);
        lotteryMarkSix.setFourColor(markSixColorList.get(new Random().nextInt(3)));
        lotteryMarkSix.setFive(new Random().nextInt(49) + 1);
        lotteryMarkSix.setFiveColor(markSixColorList.get(new Random().nextInt(3)));
        lotteryMarkSix.setSix(new Random().nextInt(49) + 1);
        lotteryMarkSix.setSixColor(markSixColorList.get(new Random().nextInt(3)));
        lotteryMarkSix.setSpecial(new Random().nextInt(49) + 1);
        lotteryMarkSix.setSpecialColor(markSixColorList.get(new Random().nextInt(3)));

        lotteryMarkSix.setIssue((int) (System.currentTimeMillis() / 1000000));

        lotteryMarkSix.setTimestamp(new Date());

        System.out.println(lotteryMarkSix);
        lotteryService.saveLotteryMarkSix(lotteryMarkSix);
    }
}
