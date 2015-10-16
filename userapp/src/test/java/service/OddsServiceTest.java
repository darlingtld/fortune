package service;

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
        Odds odds = new Odds();
        odds.setLotteryMarkSix(lotteryService.getLotteryMarkSixAll().get(0));
        odds.setValue(10);
        odds.setpGroup(pGroupService.getGroupAll().get(0));
        odds.setUpdateTime(new Date());
        oddsService.saveOdds(odds);
    }

    @Test
    public void getOdds() {
        for (Odds odds : oddsService.getAll()) {
            System.out.println(odds);
        }
    }
}
