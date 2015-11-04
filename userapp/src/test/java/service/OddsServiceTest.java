package service;

import fortune.pojo.LotteryBall;
import fortune.pojo.LotteryMarkSixType;
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
        int issue = lotteryService.getNextLotteryMarkSixInfo().getInteger("issue");
        for(LotteryBall ball : LotteryBall.values()){
            LotteryOdds odds = new LotteryOdds();
            odds.setLotteryBallNumber(ball.getNumber());
            odds.setGroupId("563338f6e708fad8259ea83f");
            odds.setOdds(42.5);
            odds.setLotteryIssue(issue);
            odds.setTimestamp(new Date());
            oddsService.saveOdds(odds);
        }
    }

    @Test
    public void getOdds() {
        for (LotteryOdds odds : oddsService.getAll()) {
            System.out.println(odds);
        }
    }

    @Test
    public void getOdds4Number() {
        LotteryOdds odds = oddsService.getOdds4LotteryIssue(102, "", 15);
        System.out.println(odds);
    }
}
