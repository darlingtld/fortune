package fortune.controller;

import fortune.pojo.LotteryMarkSixWager;
import fortune.service.WagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
    private WagerService wagerService;

    @RequestMapping(value = "wage", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void wageLotteryMarkSix(@RequestBody @Valid LotteryMarkSixWager lotteryMarkSixWager, HttpServletResponse response) {
        wagerService.saveLotteryMarkSixWager(lotteryMarkSixWager);
    }

    @RequestMapping(value = "wage/delete/{lottery_mark_six_wager_id}", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void deleteLotteryMarkSixWager(@PathVariable("lottery_mark_six_wager_id") String lotteryMarkSixWagerId, HttpServletResponse response) {
        wagerService.deleteLotteryMarkSixWager(lotteryMarkSixWagerId);
    }

    @RequestMapping(value = "wage_record/{user_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryMarkSixWager> getWageRecord4LotteryMarkSix(@PathVariable("user_id") int userId, HttpServletResponse response) {
        return wagerService.getLotteryMarkSixWagerList(userId);
    }

    @RequestMapping(value = "wage_record/{user_id}/{lottery_issue}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryMarkSixWager> getWageRecord4LotteryMarkSix(@PathVariable("user_id") int userId, @PathVariable("lottery_issue") int lotteryIssue, HttpServletResponse response) {
        return wagerService.getLotteryMarkSixWagerList(userId, lotteryIssue);
    }

}