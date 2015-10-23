package fortune.controller;

import common.Utils;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryMarkSixWager;
import fortune.service.WagerService;
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
    private WagerService wagerService;

    /**
     * 玩家按数字下注
     * LotteryMarkSixWager里面有个type属性，见LotteryMarkSixType类
     * 如果按类型下注， LotteryMarkSixType必选
     * 如果按数字下注，LotteryMarkSixType设为LotteryMarkSixType.NUMBER
     *
     * @param lotteryMarkSixWager
     * @param response
     */
    @RequestMapping(value = "wage", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void wageLotteryMarkSix(@RequestBody @Valid LotteryMarkSixWager lotteryMarkSixWager, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            return;
        }
        wagerService.saveLotteryMarkSixWager(lotteryMarkSixWager);
    }

    /**
     * 根据id查询某笔下注
     * @param lotteryMarkSixWagerId
     * @param response
     * @return
     */
    @RequestMapping(value = "wage/{lottery_mark_six_wager_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    LotteryMarkSixWager getWageLotteryMarkSixById(@PathVariable("lottery_mark_six_wager_id") String lotteryMarkSixWagerId, HttpServletResponse response) {
        return wagerService.getLotteryMarkSixWager(lotteryMarkSixWagerId);
    }

    /**
     * 玩家修改某次下注,返回最新的下注对象
     *
     * @param lotteryMarkSixWager
     * @param result
     * @param response
     * @return
     */
    @RequestMapping(value = "wage/update", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    LotteryMarkSixWager updateLotteryMarkSixByNumber(@RequestBody @Valid LotteryMarkSixWager lotteryMarkSixWager, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            return null;
        }
        return wagerService.updateLotteryMarkSixWager(lotteryMarkSixWager);
    }

    /**
     * 玩家删除某次下注
     *
     * @param lotteryMarkSixWagerId
     * @param response
     */
    @RequestMapping(value = "wage/delete/{lottery_mark_six_wager_id}", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void deleteLotteryMarkSixWager(@PathVariable("lottery_mark_six_wager_id") String lotteryMarkSixWagerId, HttpServletResponse response) {
        wagerService.deleteLotteryMarkSixWager(lotteryMarkSixWagerId);
    }

    /**
     * 获取玩家的下注历史
     *
     * @param userId
     * @param response
     * @return
     */
    @RequestMapping(value = "wage_record/{user_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryMarkSixWager> getWageRecord4LotteryMarkSix(@PathVariable("user_id") int userId, HttpServletResponse response) {
        return wagerService.getLotteryMarkSixWagerList(userId);
    }

    /**
     * 获取某一期的玩家下注记录
     *
     * @param userId
     * @param lotteryIssue
     * @param response
     * @return
     */
    @RequestMapping(value = "wage_record/{user_id}/lottery_issue/{lottery_issue}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryMarkSixWager> getWageRecord4LotteryMarkSix(@PathVariable("user_id") int userId, @PathVariable("lottery_issue") int lotteryIssue, HttpServletResponse response) {
        return wagerService.getLotteryMarkSixWagerList(userId, lotteryIssue);
    }

    /**
     * 获取某一期的某一代理商下玩家下注记录
     *
     * @param userId
     * @param lotteryIssue
     * @param pgroupId
     * @param response
     * @return
     */
    @RequestMapping(value = "wage_record/{user_id}/pgroup/{pgroup_id}/lottery_issue/{lottery_issue}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryMarkSixWager> getWageRecord4LotteryMarkSix(@PathVariable("user_id") int userId, @PathVariable("pgroup_id") int pgroupId, @PathVariable("lottery_issue") int lotteryIssue, HttpServletResponse response) {
        return wagerService.getLotteryMarkSixWagerList(userId, pgroupId, lotteryIssue);
    }

}