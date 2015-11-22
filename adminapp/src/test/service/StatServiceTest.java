package service;

import fortune.pojo.*;
import fortune.service.LotteryService;
import fortune.service.PGroupService;
import fortune.service.StatService;
import fortune.service.WagerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Random;

/**
 * Created by tangl9 on 2015-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class StatServiceTest {

    @Autowired
    private StatService statService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private PGroupService pGroupService;

    @Autowired
    private WagerService wagerService;

    @Test
    public void getLotteryMarkSixStatList() {
        String pgroupId = "56395fa0e7081d3714335213";
        int from = 0;
        int count = 10;
        List<LotteryMarkSixStat> lotteryMarkSixStatList = statService.getLotteryMarkSixStat(pgroupId, from, count);
        lotteryMarkSixStatList.forEach(System.out::println);
    }

    @Test
    public void createLotteryMarkSixStatList() {
        for (PGroup pGroup : pGroupService.getGroupAll()) {
            String pgroupId = pGroup.getId();
            List<LotteryMarkSix> lotteryMarkSixList = lotteryService.getLotteryMarkSixAll();
            for (LotteryMarkSix lotteryMarkSix : lotteryMarkSixList) {
                LotteryMarkSixStat stat = new LotteryMarkSixStat();
                stat.setLotteryMarkSix(lotteryMarkSix);
                stat.setPgroupId(pgroupId);
                stat.setPgroupResult(new Random().nextDouble() * 1000000);
                stat.setPgroupTotalResult(new Random().nextDouble() * 1000000);
                stat.setTotalStakes(new Random().nextDouble() * 1000000);
                stat.setUserResult(new Random().nextDouble() * 1000000);
                stat.setZoufeiResult(new Random().nextDouble() * 1000000);
                stat.setZoufeiStakes(new Random().nextDouble() * 1000000);
                statService.saveLotteryMarkSixStat(stat);
            }
        }
    }

    @Test
    public void getWagerList() {
        int issue = 300;
        String groupId = "563338f6e708fad8259ea83f";
        int number = 1;
        List<LotteryMarkSixWager> list = wagerService.getLotteryMarkSixWagerListOfSpecialNumber(groupId, issue, number);
        for (LotteryMarkSixWager wager : list) {
            System.out.println(wager);
        }
    }
}
