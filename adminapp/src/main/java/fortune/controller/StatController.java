package fortune.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    @RequestMapping(value = "realtime/transaction_result/{type}/groupid/{groupid}/pan/{panlei}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<RealtimeStat> getRealTimeTransactionResult(@PathVariable("type") String type, @PathVariable("groupid") String groupId, @PathVariable("panlei") String panlei) {
        return statService.getRealTimeTransactionResult(LotteryMarkSixType.valueOf(type.toUpperCase()), groupId, panlei.toUpperCase());
    }

    /**
     * 获取每个种类即时注单的交易总数
     *
     * @param groupId
     * @return
     */
    @RequestMapping(value = "realtime/transaction_result/total_count/groupid/{groupid}/pan/{panlei}/issue/{issue}", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONObject getRealTimeTransactionTotalCount(@PathVariable("groupid") String groupId, @PathVariable("panlei") String panlei, @PathVariable("issue") int issue) {
        return statService.getRealTimeTransactionTotalCount(groupId, panlei, issue);
    }

    @RequestMapping(value = "realtime/stake_detail/{type}/groupid/{groupid}/pan/{panlei}/issue/{issue}/ball/{number}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<RealTimeWager> getStakeDetail4Special(@PathVariable("type") String type, @PathVariable("groupid") String groupId, @PathVariable("panlei") String panlei, @PathVariable("issue") int issue, @PathVariable("number") int number) {
        return statService.getStakeDetail(LotteryMarkSixType.valueOf(type.toUpperCase()), groupId, panlei.toUpperCase(), issue, number, null);
    }
    
    @RequestMapping(value = "realtime/stake_detail/{type}/groupid/{groupid}/pan/{panlei}/issue/{issue}/subtype/{subtype}/ball/{number}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<RealTimeWager> getStakeDetail(@PathVariable("type") String type, @PathVariable("groupid") String groupId, @PathVariable("panlei") String panlei, @PathVariable("issue") int issue, @PathVariable("number") int number, @PathVariable("subtype") String subtype) {
        return statService.getStakeDetail(LotteryMarkSixType.valueOf(type.toUpperCase()), groupId, panlei.toUpperCase(), issue, number, LotteryMarkSixType.valueOf(subtype.toUpperCase()));
    }

}
