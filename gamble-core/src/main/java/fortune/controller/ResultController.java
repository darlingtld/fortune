package fortune.controller;

import fortune.pojo.LotteryResult;
import fortune.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lingda on 2015/10/24.
 */
@Scope("prototype")
@Controller
@RequestMapping("/result")
public class ResultController {

    @Autowired
    private ResultService resultService;

    /**
     * 获取某个用户的某期中奖结果，会包含所有代理商的结果，是个list
     *
     * @param lotteryIssue
     * @param userId
     * @return
     */
    @RequestMapping(value = "lottery_issue/{lottery_issue}/user_id/{user_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryResult> getLotteryResult4User(@PathVariable("lottery_issue") int lotteryIssue, @PathVariable("user_id") int userId) {
        return resultService.getLotteryResult4LotteryIssueAndUser(lotteryIssue, userId);
    }

    /**
     * 获取某个用户在一个代理商下的的某期中奖结果
     *
     * @param lotteryIssue
     * @param pgroupId
     * @param userId
     * @return
     */
    @RequestMapping(value = "lottery_issue/{lottery_issue}/pgroup/{pgroup_id}/user_id/{user_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    LotteryResult getLotteryResult4User(@PathVariable("lottery_issue") int lotteryIssue, @PathVariable("pgroup_id") int pgroupId, @PathVariable("user_id") int userId) {
        return resultService.getLotteryResult4LotteryIssue(lotteryIssue, pgroupId, userId);
    }

    /**
     * 根据中奖结果id，查询某次中奖结果
     *
     * @param lotteryResultId
     * @return
     */
    @RequestMapping(value = "lottery_result_id/{lottery_result_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    LotteryResult getLotteryResultById(@PathVariable("lottery_result_id") int lotteryResultId) {
        return resultService.getLotteryResultById(lotteryResultId);
    }

    /**
     * 根据用户id查询用户的所有中奖纪录
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "user_id/{user_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryResult> getLotteryResult4User(@PathVariable("user_id") int userId) {
        return resultService.getLotteryResult4User(userId);
    }

}
