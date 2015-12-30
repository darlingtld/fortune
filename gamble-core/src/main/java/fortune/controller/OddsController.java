package fortune.controller;

import com.alibaba.fastjson.JSONObject;
import common.Utils;
import fortune.pojo.LotteryOdds;
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

/**
 * Created by tangl9 on 2015-10-19.
 */
@Scope("prototype")
@Controller
@RequestMapping("/odds")
public class OddsController {

    @Autowired
    private OddsService oddsService;

    /**
     * 设置赔率
     * 如果设置了type类型，在中奖判断时，会忽略number的情况
     *
     * @param odds
     * @param result
     * @param response
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void saveOdds4LotteryMarkSix(@RequestBody @Valid LotteryOdds odds, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            return;
        }
        oddsService.saveOdds(odds);
    }

    /**
     * 修改赔率,根据odds Id修改赔率
     */
    @RequestMapping(value = "change", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    LotteryOdds changeOddsById(@RequestBody JSONObject oddsStub, HttpServletResponse response) {
        String oddsId = oddsStub.getString("oddsId");
        double odds = oddsStub.getDouble("odds");
        return oddsService.changeOdds(oddsId, odds);
    }

    /**
     * 获取所有赔率
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryOdds> getOddsAll(HttpServletResponse response) {
        return oddsService.getAll();
    }

    /**
     * 根据id获取赔率
     *
     * @param oddsId
     * @return
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    LotteryOdds getOddsById(@PathVariable("id") String oddsId) {
        return oddsService.getOddsById(oddsId);
    }

    /**
     * 获取某个代理商对于某一期的博彩的赔率
     * 后台有守护进程在每期开奖之后，自动复制代理商的上一期赔率
     *
     * @param lotteryIssue 博彩的期数
     * @param groupId      代理商的id
     * @return
     */
    @RequestMapping(value = "lottery_issue/{lottery_issue}/group/{group_id}/pan/{panlei}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryOdds> getOdds4LotteryIssue(@PathVariable("lottery_issue") int lotteryIssue, @PathVariable("group_id") String groupId, @PathVariable("panlei") String panlei) {
        return oddsService.getOdds4LotteryIssue(lotteryIssue, groupId, panlei.toUpperCase());
    }

    /**
     * 获取某个代理商对于某一期的博彩的某一个数字的赔率
     * 后台有守护进程在每期开奖之后，自动复制代理商的上一期赔率
     *
     * @param lotteryIssue 博彩的期数
     * @param groupId      代理商的id
     * @param number       球的数字
     * @return
     */
    @Deprecated
    @RequestMapping(value = "lottery_issue/{lottery_issue}/group/{group_id}/ball_number/{number}/pan/{panlei}", method = RequestMethod.GET)
    public
    @ResponseBody
    LotteryOdds getOdds4LotteryIssueAndNumber(@PathVariable("lottery_issue") int lotteryIssue, @PathVariable("group_id") String groupId, @PathVariable("number") int number, @PathVariable("panlei") String panlei) {
        return oddsService.getOdds4LotteryIssue(lotteryIssue, groupId, number, panlei);
    }

    /**
     * 获取某个代理商对于某一期的博彩的某一种类型的赔率
     *
     * @param lotteryIssue
     * @param groupId
     * @param lotteryMarkSixType
     * @return
     */
    @RequestMapping(value = "lottery_issue/{lottery_issue}/group/{group_id}/lottery_mark_six_type/{lottery_mark_six_type}/pan/{panlei}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryOdds> getOdds4LotteryIssueAndType(@PathVariable("lottery_issue") int lotteryIssue, @PathVariable("group_id") String groupId, @PathVariable("lottery_mark_six_type") String lotteryMarkSixType, @PathVariable("panlei") String panlei) {
        return oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupId, lotteryMarkSixType.toUpperCase(), panlei.toUpperCase());
    }

}
