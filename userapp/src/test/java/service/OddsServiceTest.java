package service;

import fortune.pojo.LotteryBall;
import fortune.pojo.LotteryOdds;
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

import java.util.Date;

/**
 * Created by tangl9 on 2015-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class OddsServiceTest {

    @Autowired
    private OddsService oddsService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private UserService userService;

    @Autowired
    private PGroupService pGroupService;

    @Test
    public void saveOdds() {
        LotteryOdds odds = new LotteryOdds();
        odds.setLotteryBallNumber(LotteryBall.NUM_25.getNumber());
        odds.setGroupId(3);
        odds.setOdds(42.5);
        odds.setLotteryIssue(102);
        odds.setTimestamp(new Date());
        oddsService.saveOdds(odds);
    }

    @Test
    public void getOdds() {
        for (LotteryOdds odds : oddsService.getAll()) {
            System.out.println(odds);
        }
    }

    @Test
    public void getOdds4Number() {
        LotteryOdds odds = oddsService.getOdds4LotteryIssue(102, 3, 15);
        System.out.println(odds);
    }
}
