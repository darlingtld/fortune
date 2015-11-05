package service;

import fortune.pojo.LotteryBall;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryOdds;
import fortune.pojo.PGroup;
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
import java.util.List;

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
        List<PGroup> pGroupList = pGroupService.getGroupAll();
        for (int i = 0; i < pGroupList.size(); i++) {
            int issue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
            for (LotteryBall ball : LotteryBall.values()) {
                LotteryOdds odds = new LotteryOdds();
                odds.setLotteryBallNumber(ball.getNumber());
                odds.setGroupId(pGroupList.get(i).getId());
                odds.setOdds(20 + i);
                odds.setLotteryIssue(issue);
                odds.setTimestamp(new Date());
                oddsService.saveOdds(odds);
            }
        }
    }

    @Test
    public void getOdds() {
        for (LotteryOdds odds : oddsService.getAll()) {
            System.out.println(odds);
        }
    }

    @Test
    public void updateOdds() {
//        oddsService.
    }

    @Test
    public void getOdds4Number() {
        LotteryOdds odds = oddsService.getOdds4LotteryIssue(102, "", 15);
        System.out.println(odds);
    }
}
