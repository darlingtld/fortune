package fortune.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.RealTimeWager;
import fortune.service.StakeDetailService;

/**
 * Created by Jason on 2016-01-13.
 */
@Scope("prototype")
@Controller
@RequestMapping("/stake_detail")
public class StakeDetailController {

    @Autowired
    StakeDetailService stakeDetailService;
    
    /**
     * 查找某代理商某一时间段内的注单详情
     */
    @RequestMapping(value = "/groupid/{groupid}/from/{fromDate}/to/{toDate}", method = RequestMethod.GET)
    public @ResponseBody List<RealTimeWager> getStakeDetailByTimeRange(
            @PathVariable("groupid") String groupId,
            @PathVariable("fromDate") String fromDate,
            @PathVariable("toDate") String toDate,
            @RequestParam(value = "userid", required = false) String userId,
            @RequestParam(value = "type", required = false) String type) {
            return stakeDetailService.getStakeDetailByTimeRange(groupId, userId, type, fromDate, toDate);
    }
    
    /**
     * 获取某类型注单详情
     */
    @RequestMapping(value = "/groupid/{groupid}/type/{type}/pan/{panlei}/issue/{issue}/ball/{number}", method = RequestMethod.GET)
    public @ResponseBody List<RealTimeWager> getStakeDetail(
            @PathVariable("type") String type,
            @PathVariable("groupid") String groupId, 
            @PathVariable("panlei") String panlei,
            @PathVariable("issue") int issue, 
            @PathVariable("number") int number,
            @RequestParam(value = "subtype", required = false) String subtypeStr,
            @RequestParam(value = "content", required = false) String content) {
        LotteryMarkSixType subtype = subtypeStr == null ? null : LotteryMarkSixType.valueOf(subtypeStr.toUpperCase());
        return stakeDetailService.getStakeDetail(LotteryMarkSixType.valueOf(type.toUpperCase()), groupId, panlei.toUpperCase(), issue, number, subtype, content);
    }
    
    @RequestMapping(value = "/groupid/{groupid}/all/type/{type}/pan/{panlei}/issue/{issue}", method = RequestMethod.GET)
    public @ResponseBody List<RealTimeWager> getAllStakeDetail4Type(
            @PathVariable("type") String type,
            @PathVariable("groupid") String groupId, 
            @PathVariable("panlei") String panlei,
            @PathVariable("issue") int issue) {
        return stakeDetailService.getAllStakeDetail4Type(LotteryMarkSixType.valueOf(type.toUpperCase()), groupId, panlei.toUpperCase(), issue);
    }
 
    @RequestMapping(value = "/{user}/groupid/{groupid}/issue/{issue}", method = RequestMethod.GET)
    public @ResponseBody List<RealTimeWager> getAllStakeDetail4User(
            @PathVariable("user") String userId,
            @PathVariable("groupid") String groupId,
            @PathVariable("issue") int issue) {
        return stakeDetailService.getAllStakeDetail4User(userId, groupId, issue);
    }
}
