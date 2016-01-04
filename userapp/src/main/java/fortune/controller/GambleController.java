package fortune.controller;

import common.Utils;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryMarkSixWager;
import fortune.service.ActionTraceService;
import fortune.service.PGroupService;
import fortune.service.UserService;
import fortune.service.WagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Scope("prototype")
@Controller
@RequestMapping("/gamble")
public class GambleController {

    @Autowired
    private WagerService wagerService;

    @Autowired
    private ActionTraceService actionTraceService;

    @Autowired
    private UserService userService;

    @Autowired
    private PGroupService pGroupService;

    /**
     * 玩家按数字下注
     * LotteryMarkSixWager里面有个type属性，见LotteryMarkSixType类
     * 如果按类型下注， LotteryMarkSixType必选
     * 如果按数字下注，LotteryMarkSixType设为LotteryMarkSixType.NUMBER
     * <p>
     * 例如：
     * 1.按特码下注，类型为特码单，LotteryMarkSixType设为LotteryMarkSixType.SPECIAL_DAN
     * 此时，lotteryMarkSixWagerStubList可以不写，其他的要给我填上
     * 2.如果玩家下注按数字下注，比如买1号球10注，7号球15注
     * 那么lotteryMarkSixWagerStubList=[{number:1,stakes:10},{number:7,stakes15}],LotteryMarkSixType设为LotteryMarkSixType.NUMBER
     * 3.如果有些规则需要对lotteryMarkSixWagerStubList和LotteryMarkSixType都有设置的话，我会先根据type判断，如有需要，再去拿stublist里东西和开奖结果做对比
     *
     * @param lotteryMarkSixWager
     * @param response
     */
    @RequestMapping(value = "wage", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void wageLotteryMarkSix(@RequestBody @Valid LotteryMarkSixWager lotteryMarkSixWager, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            return;
        }
        try {
            wagerService.saveLotteryMarkSixWager(lotteryMarkSixWager);
        } catch (ArithmeticException e) {
            Utils.logger.debug(e.getMessage());
            response.setHeader(Utils.HEADER_MESSAGE, e.getMessage());
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        }
        String trace = String.format("wage on %s [pan %s]", pGroupService.getGroupById(lotteryMarkSixWager.getPgroupId()).getName(), lotteryMarkSixWager.getPanlei());
        actionTraceService.save(userService.getUserById(lotteryMarkSixWager.getUserId()).getUsername(), trace, request);
    }

    @RequestMapping(value = "wages", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void wageMultipleLotteryMarkSix(@RequestBody @Valid LotteryMarkSixWager[] lotteryMarkSixWagers, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            return;
        }
        try {
            wagerService.saveLotteryMarkSixWager(Arrays.asList(lotteryMarkSixWagers));
        } catch (ArithmeticException e) {
            Utils.logger.debug(e.getMessage());
            response.setHeader(Utils.HEADER_MESSAGE, e.getMessage());
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        }

    }

    /**
     * 根据id查询某笔下注
     *
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
    List<LotteryMarkSixWager> getWageRecord4LotteryMarkSix(@PathVariable("user_id") String userId, HttpServletResponse response) {
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
    List<LotteryMarkSixWager> getWageRecord4LotteryMarkSix(@PathVariable("user_id") String userId, @PathVariable("lottery_issue") int lotteryIssue, HttpServletResponse response) {
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
    List<LotteryMarkSixWager> getWageRecord4LotteryMarkSix(@PathVariable("user_id") String userId, @PathVariable("pgroup_id") String pgroupId, @PathVariable("lottery_issue") int lotteryIssue, HttpServletResponse response) {
        return wagerService.getLotteryMarkSixWagerList(userId, pgroupId, lotteryIssue);
    }

}