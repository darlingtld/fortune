package fortune.controller;

import fortune.pojo.GambleBetLotteryMarkSix;
import fortune.pojo.Odds;
import fortune.service.LotteryService;
import fortune.service.OddsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Scope("prototype")
@Controller
@RequestMapping("/gamble")
public class GambleController {

    @Autowired
    private OddsService oddsService;

    @Autowired
    private LotteryService lotteryService;

    @RequestMapping(value = "wage", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void wageLotteryMarkSix(@RequestBody GambleBetLotteryMarkSix gambleBetLotteryMarkSix, HttpServletResponse response) {
        lotteryService.saveGambleBetLotteryMarkSix(gambleBetLotteryMarkSix);
    }

    @RequestMapping(value = "wage/delete/{gamble_bet_lottery_mark_six_id}", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void deleteGambleBetLotteryMarkSix(@PathVariable("gamble_bet_lottery_mark_six_id") int gambleBetLotteryMarkSixId, HttpServletResponse response) {
        lotteryService.deleteGambleBetLotteryMarkSix(gambleBetLotteryMarkSixId);
    }

    @RequestMapping(value = "wage_record/{user_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<GambleBetLotteryMarkSix> getWageRecord4LotteryMarkSix(@PathVariable("user_id") int userId, HttpServletResponse response) {
        return lotteryService.getGambleBetLotteryMarkSixList(userId);
    }

    @RequestMapping(value = "odds/{odds_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Odds getOddsById(@PathVariable("odds_id") int oddsId) {
        return oddsService.getOddsById(oddsId);
    }

    @RequestMapping(value = "odds/lottery/{lottery_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Odds> getOdds4Lottery(@PathVariable("lottery_id") int lotteryId) {
        return oddsService.getOdds4Lottery(lotteryId);
    }
}