package service;

import fortune.pojo.LotteryMarkSixWager;
import fortune.pojo.LotteryMarkSixWagerStub;
import fortune.pojo.User;
import fortune.service.UserService;
import fortune.service.WagerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class WagerServiceTest {

    @Autowired
    private WagerService wagerService;

    @Autowired
    private UserService userService;

    @Test
    public void saveLotteryMarkSixWager() {
        LotteryMarkSixWager wager = new LotteryMarkSixWager();
        wager.setUserId(2);
        wager.setLotteryIssue(102);
        wager.setTotalMoney(100000);
        wager.setTotalStakes(200);
        wager.setTimestamp(new Date());
        LotteryMarkSixWagerStub stub = new LotteryMarkSixWagerStub();
        stub.setNumber(25);
        stub.setStakes(1000);
        LotteryMarkSixWagerStub stub1 = new LotteryMarkSixWagerStub();
        stub1.setNumber(7);
        stub1.setStakes(2000);
        wager.setLotteryMarkSixWagerStubList(Arrays.asList(stub, stub1));
        wagerService.saveLotteryMarkSixWager(wager);
    }

    @Test
    public void getLotteryMarkSixWagerList() {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(2, 102);
        for (LotteryMarkSixWager lotteryMarkSixWager : wagerList) {
            System.out.println(lotteryMarkSixWager);
            for(LotteryMarkSixWagerStub stub : lotteryMarkSixWager.getLotteryMarkSixWagerStubList()){
                System.out.println(stub);
            }
        }
    }

}
