package fortune.rule;

import common.Utils;
import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryMarkSixWager;
import fortune.pojo.LotteryResult;
import fortune.service.LotteryService;
import fortune.service.OddsService;
import fortune.service.ResultService;
import fortune.service.WagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-26.
 */
//正码特单，特单
@Scope("prototype")
@Component
public class RuleSPECIALDAN implements Runnable {

    private int lotteryIssue;
    @Autowired
    private WagerService wagerService;
    @Autowired
    private LotteryService lotteryService;
    @Autowired
    private ResultService resultService;
    @Autowired
    private OddsService oddsService;

    public int getLotteryIssue() {
        return lotteryIssue;
    }

    public void setLotteryIssue(int lotteryIssue) {
        this.lotteryIssue = lotteryIssue;
    }

    @Override
    public void run() {
        Utils.logger.info("rule [SPECIALDAN] runs...");
//        get all wagers for this issue
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerListByType(lotteryIssue, LotteryMarkSixType.SPECIAL_DAN);
//        run against rules
        LotteryMarkSix lotteryMarkSix = lotteryService.getLotteryMarkSix(lotteryIssue);
        HashMap<String, Double> oddsCache = new HashMap<>();
        for (LotteryMarkSixWager wager : wagerList) {
            LotteryResult lotteryResult = new LotteryResult();
            lotteryResult.setUserId(wager.getUserId());
            lotteryResult.setGroupId(wager.getPgroupId());
            lotteryResult.setLotteryIssue(lotteryIssue);
            lotteryResult.setLotteryMarkSixWagerId(wager.getId());
//            win
            if (lotteryMarkSix.getSpecial() % 2 == 1) {
                String oddsCacheKey = String.format("%s#%s#%s", wager.getLotteryIssue(), wager.getPgroupId(), wager.getLotteryMarkSixType());
                Double odds = oddsCache.get(oddsCacheKey);
                if (odds == null) {
                    odds = oddsService.getOdds4LotteryIssueByType(lotteryIssue, wager.getPgroupId(), LotteryMarkSixType.SPECIAL_DAN.name()).getOdds();
                    oddsCache.put(oddsCacheKey, odds);
                }
                double winningMoney = wager.getTotalMoney() * odds;
                lotteryResult.setWinningMoney(winningMoney);
            }
//            lose
            else {
                lotteryResult.setWinningMoney(0);
            }
            resultService.saveLotteryResult(lotteryResult);
        }

        Utils.logger.info("rule [SPECIALDAN] finished");
    }
}
