package service;

import fortune.pojo.*;
import fortune.rule.RuleSPECIALDAN;
import fortune.service.LotteryService;
import fortune.service.OddsService;
import fortune.service.UserService;
import fortune.service.WagerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
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
    private OddsService oddsService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private RuleSPECIALDAN ruleSPECIALDAN;

    @Test
    public void saveLotteryMarkSixWager() {
        LotteryMarkSixWager wager = new LotteryMarkSixWager();
        wager.setUserId(2);
        wager.setPgroupId("");
        wager.setLotteryIssue(102);
        wager.setTotalStakes(100000);
        wager.setTotalStakes(200);
        wager.setTimestamp(new Date());
        LotteryMarkSixWagerStub stub = new LotteryMarkSixWagerStub();
        stub.setNumber(25);
        stub.setStakes(1000);
        LotteryMarkSixWagerStub stub1 = new LotteryMarkSixWagerStub();
        stub1.setNumber(7);
        stub1.setStakes(2000);
        wager.setLotteryMarkSixWagerStubList(Arrays.asList(stub, stub1));
        wager.setLotteryMarkSixType(LotteryMarkSixType.HEXIAO);
        wagerService.saveLotteryMarkSixWager(wager);
    }

    @Test
    public void getLotteryMarkSixWagerList() {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(2, 4, 102);
        for (LotteryMarkSixWager lotteryMarkSixWager : wagerList) {
            System.out.println(lotteryMarkSixWager);
            lotteryMarkSixWager.getLotteryMarkSixWagerStubList().forEach(System.out::println);
        }
    }

    @Test
    public void updateLotteryMarkSixWager() {
        LotteryMarkSixWager wager = wagerService.getLotteryMarkSixWager("5629e5e5e708c84fd0ad85cd");
        System.out.println(wager);
        wager.setTotalStakes(5555);
        List<LotteryMarkSixWagerStub> lotteryMarkSixWagerStubList = wager.getLotteryMarkSixWagerStubList();
        lotteryMarkSixWagerStubList.add(new LotteryMarkSixWagerStub(17, 400));
        wager.setLotteryMarkSixWagerStubList(lotteryMarkSixWagerStubList);
        wager = wagerService.updateLotteryMarkSixWager(wager);
        System.out.println(wager);
    }


    @Test
    public void mimicWage() {
        int lotteryIssue=163;
//        user wages
        LotteryMarkSixWager wager = new LotteryMarkSixWager();
        wager.setUserId(1);
        wager.setPgroupId("");
        wager.setLotteryIssue(lotteryIssue);
        wager.setTotalStakes(300);
        wager.setTotalStakes(10);
        wager.setLotteryMarkSixType(LotteryMarkSixType.SPECIAL_SHUANG);
        wagerService.saveLotteryMarkSixWager(wager);
    }

    @Test
    public void mimicWageAndDraw() {
        int lotteryIssue=163;
//        user wages
        LotteryMarkSixWager wager = new LotteryMarkSixWager();
        wager.setUserId(1);
        wager.setPgroupId("");
        wager.setLotteryIssue(lotteryIssue);
        wager.setTotalStakes(300);
        wager.setLotteryMarkSixType(LotteryMarkSixType.SPECIAL_DAN);
        wagerService.saveLotteryMarkSixWager(wager);
//        group admin set odds
        LotteryOdds odds = new LotteryOdds();
        odds.setLotteryIssue(lotteryIssue);
        odds.setGroupId("563338f6e708fad8259ea83f");
        odds.setLotteryMarkSixType(LotteryMarkSixType.SPECIAL_DAN);
        odds.setOdds(42);
        oddsService.saveOdds(odds);
//        lottery mark six draws
        LotteryMarkSix lotteryMarkSix = new LotteryMarkSix();
        lotteryMarkSix.setOne(33);
        lotteryMarkSix.setTwo(12);
        lotteryMarkSix.setThree(8);
        lotteryMarkSix.setFour(28);
        lotteryMarkSix.setFive(19);
        lotteryMarkSix.setSix(45);
        lotteryMarkSix.setSpecial(9);
        lotteryMarkSix.setIssue(lotteryIssue);
        lotteryService.saveLotteryMarkSix(lotteryMarkSix);

        ruleSPECIALDAN.run();
    }

    @Test
    public void runRules(){
        ruleSPECIALDAN.run();
    }

}
