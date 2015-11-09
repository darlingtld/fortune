package service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fortune.pojo.LotteryBall;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryOdds;
import fortune.pojo.PGroup;
import fortune.service.LotteryService;
import fortune.service.OddsService;
import fortune.service.PGroupService;
import fortune.service.UserService;

/**
 * Created by tangl9 on 2015-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:mvc-dispatcher-servlet.xml" })
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
    public void generateColorLotteryOdds() {
        List<PGroup> pGroupList = pGroupService.getGroupAll();
        for (int i = 0; i < pGroupList.size(); i++) {
			LotteryOdds odds = new LotteryOdds();
			odds.setGroupId(pGroupList.get(i).getId());
			odds.setOdds(20 + i);
			odds.setLotteryIssue(300);
			odds.setTimestamp(new Date());
			odds.setLotteryMarkSixType(LotteryMarkSixType.BLUE);
			oddsService.saveOdds(odds);
			
			odds = new LotteryOdds();
			odds.setGroupId(pGroupList.get(i).getId());
			odds.setOdds(20 + i);
			odds.setLotteryIssue(300);
			odds.setTimestamp(new Date());
			odds.setLotteryMarkSixType(LotteryMarkSixType.GREEN);
			oddsService.saveOdds(odds);
			
			odds = new LotteryOdds();
			odds.setGroupId(pGroupList.get(i).getId());
			odds.setOdds(20 + i);
			odds.setLotteryIssue(300);
			odds.setTimestamp(new Date());
			odds.setLotteryMarkSixType(LotteryMarkSixType.RED);
			oddsService.saveOdds(odds);
        }
    }
	
	@Test
    public void generateSpecialLotteryOdds() {
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
                odds.setLotteryMarkSixType(LotteryMarkSixType.SPECIAL);
                oddsService.saveOdds(odds);
            }
        }
    }
	
	@Test
    public void generateZodiacLotteryOdds() {
        List<PGroup> pGroupList = pGroupService.getGroupAll();
        for (int i = 0; i < pGroupList.size(); i++) {
            int issue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
            LotteryOdds odds = new LotteryOdds();
            odds.setGroupId(pGroupList.get(i).getId());
            odds.setOdds(1);
            odds.setLotteryIssue(issue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_GOU);
            oddsService.saveOdds(odds);
            odds = new LotteryOdds();
            odds.setGroupId(pGroupList.get(i).getId());
            odds.setOdds(1);
            odds.setLotteryIssue(issue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_HOU);
            oddsService.saveOdds(odds);
            odds = new LotteryOdds();
            odds.setGroupId(pGroupList.get(i).getId());
            odds.setOdds(1);
            odds.setLotteryIssue(issue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_HU);
            oddsService.saveOdds(odds);
            odds = new LotteryOdds();
            odds.setGroupId(pGroupList.get(i).getId());
            odds.setOdds(1);
            odds.setLotteryIssue(issue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_JI);
            oddsService.saveOdds(odds);
            odds = new LotteryOdds();
            odds.setGroupId(pGroupList.get(i).getId());
            odds.setOdds(1);
            odds.setLotteryIssue(issue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_LONG);
            oddsService.saveOdds(odds);
            odds = new LotteryOdds();
            odds.setGroupId(pGroupList.get(i).getId());
            odds.setOdds(1);
            odds.setLotteryIssue(issue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_MA);
            oddsService.saveOdds(odds);
            odds = new LotteryOdds();
            odds.setGroupId(pGroupList.get(i).getId());
            odds.setOdds(1);
            odds.setLotteryIssue(issue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_NIU);
            oddsService.saveOdds(odds);
            odds = new LotteryOdds();
            odds.setGroupId(pGroupList.get(i).getId());
            odds.setOdds(1);
            odds.setLotteryIssue(issue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_SHE);
            oddsService.saveOdds(odds);
            odds = new LotteryOdds();
            odds.setGroupId(pGroupList.get(i).getId());
            odds.setOdds(1);
            odds.setLotteryIssue(issue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_SHU);
            oddsService.saveOdds(odds);
            odds = new LotteryOdds();
            odds.setGroupId(pGroupList.get(i).getId());
            odds.setOdds(1);
            odds.setLotteryIssue(issue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_TU);
            oddsService.saveOdds(odds);
            odds = new LotteryOdds();
            odds.setGroupId(pGroupList.get(i).getId());
            odds.setOdds(1);
            odds.setLotteryIssue(issue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_YANG);
            oddsService.saveOdds(odds);
            odds = new LotteryOdds();
            odds.setGroupId(pGroupList.get(i).getId());
            odds.setOdds(1);
            odds.setLotteryIssue(issue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_ZHU);
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
	public void updateOdds() {
		// oddsService.
	}

	@Test
	public void getOdds4Number() {
		LotteryOdds odds = oddsService.getOdds4LotteryIssue(102, "", 15);
		System.out.println(odds);
	}
}
