package fortune.controller;

import common.Utils;
import fortune.pojo.GambleBetLotteryMarkSix;
import fortune.pojo.Odds;
import fortune.service.LotteryService;
import fortune.service.OddsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    void wageLotteryMarkSix(@RequestBody @Valid GambleBetLotteryMarkSix gambleBetLotteryMarkSix, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            return;
        }
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