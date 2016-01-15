package fortune.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.RealtimeStat;
import fortune.service.StatService;

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
     * 获取代理商实时统计信息(实时注单)
     *
     * @param groupId
     * @return
     */
    @RequestMapping(value = "realtime/transaction_result/{type}/groupid/{groupid}/pan/{panlei}/issue/{issue}", method = RequestMethod.GET)
    public @ResponseBody List<RealtimeStat> getRealTimeTransactionResult(
            @PathVariable("type") String type,
            @PathVariable("groupid") String groupId, 
            @PathVariable("panlei") String panlei,
            @PathVariable("issue") int issue) {
        return statService.getRealTimeTransactionResult(LotteryMarkSixType.valueOf(type.toUpperCase()), groupId,
                panlei.toUpperCase(), issue);
    }
    
    /**
     * 获取自选不中各类型的Top20注单信息
     * 
     * @param groupId
     * @param panlei
     * @param top
     * @return
     */
    @RequestMapping(value = "realtime/transaction_result/groupid/{groupid}/pan/{panlei}/issue/{issue}/not_top/{top}", method = RequestMethod.GET)
    public @ResponseBody List<List<RealtimeStat>> getNotTopStats(
            @PathVariable("groupid") String groupId, 
            @PathVariable("panlei") String panlei,
            @PathVariable("issue") int issue,
            @PathVariable("top") int top) {
        return statService.getRealTimeTransactionResult4NotTop(groupId, panlei.toUpperCase(), issue, top);
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
    
    /**
     * 获取所有即时注单的统计信息
     *
     */
    @RequestMapping(value = "realtime/transaction_result/all/groupid/{groupid}/pan/{panlei}/issue/{issue}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<RealtimeStat> getRealTimeTransactionAllStats(@PathVariable("groupid") String groupId, @PathVariable("panlei") String panlei, @PathVariable("issue") int issue) {
        return statService.getRealTimeTransactionAllStats(groupId, panlei, issue);
    }
    
}
