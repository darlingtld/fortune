package fortune.controller;

import fortune.pojo.*;
import fortune.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by tangl9 on 2015-10-14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/stat")
public class StatController {

    @Autowired
    private StatService statService;

    /**
     * 分页获取某个代理商的历史开奖结果，输赢
     *
     * @param groupid
     * @param from
     * @param count
     * @return
     */
    @RequestMapping(value = "lottery_mark_six/groupid/{groupid}/from/{from}/count/{count}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryMarkSixGroupStat> getLotteryMarkSixPagination(@PathVariable("groupid") String groupid, @PathVariable("from") int from, @PathVariable("count") int count) {
        return statService.getLotteryMarkSixStat(groupid, from, count);
    }


    /**
     * 获取代理商实时统计信息(实时注单)
     *
     * @param groupId
     * @return
     */
    @RequestMapping(value = "realtime/transaction_result/groupid/{groupid}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<RealtimeStat> getRealTimeTransactionResult(@PathVariable("groupid") String groupId) {
        return statService.getRealTimeTransactionResult(groupId);
    }

    @RequestMapping(value = "realtime/stake_detail/special/groupid/{groupid}/issue/{issue}/ball/{number}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<RealTimeWager> getStakeDetail4Special(@PathVariable("groupid") String groupId, @PathVariable("issue") int issue, @PathVariable("number") int number) {
        return statService.getStakeDetail4Special(groupId, issue, number);
    }

}
