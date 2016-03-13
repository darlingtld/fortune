package fortune.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fortune.pojo.AdminReport;
import fortune.pojo.LotteryMarkSixGroupStat;
import fortune.pojo.LotteryMarkSixUserStat;
import fortune.service.AdminReportService;

/**
 * Created by jason on 2016-01-11.
 */
@Scope("prototype")
@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private AdminReportService adminReportService;

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
        return adminReportService.getLotteryMarkSixStat(groupid, from, count);
    }

    /**
     * 根据日期范围获取某个代理商的开奖统计信息
     */
    @RequestMapping(value = "group/mysummary/groupid/{groupid}/date_start/{start}/date_end/{end}", method = RequestMethod.GET)
    public @ResponseBody LotteryMarkSixGroupStat getGroupReportByDateRange(@PathVariable("groupid") String groupid, @PathVariable("start") String start, @PathVariable("end") String end) {
        return adminReportService.getGroupReportByDateRange(groupid, start, end);
    }
    
    /**
     * 根据日期范围获取某个代理商下所有子代理商的开奖统计信息
     */
    @RequestMapping(value = "group/summary/groupid/{groupid}/date_start/{start}/date_end/{end}", method = RequestMethod.GET)
    public @ResponseBody List<LotteryMarkSixGroupStat> getSubGroupReportByDateRange(@PathVariable("groupid") String groupid, @PathVariable("start") String start, @PathVariable("end") String end) {
        return adminReportService.getSubGroupReportByDateRange(groupid, start, end);
    }
    
    /**
     * 根据日期范围获取某个代理商的所有注单类型的报表信息
     */
    @RequestMapping(value = "group/summary/type/{type}/groupid/{groupid}/date_start/{start}/date_end/{end}", method = RequestMethod.GET)
    public @ResponseBody List<AdminReport> getSummary4Type(
            @PathVariable("type") String type,
            @PathVariable("groupid") String groupid, 
            @PathVariable("start") String start, 
            @PathVariable("end") String end) {
        if ("ALL".equalsIgnoreCase(type)) {
            return adminReportService.getReport4AllType(groupid, start, end);
        } else {
            return Arrays.asList(adminReportService.getReport4Type(type, groupid, start, end));
        }
    }
    
    /**
     * 根据日期范围获取某个代理商下所有用户的开奖统计信息
     */
    @RequestMapping(value = "user/summary/groupid/{groupid}/date_start/{start}/date_end/{end}", method = RequestMethod.GET)
    public @ResponseBody List<LotteryMarkSixUserStat> getUserSummaryByDateRange(@PathVariable("groupid") String groupid, @PathVariable("start") String start, @PathVariable("end") String end) {
        return adminReportService.getUserReportByDateRange(groupid, start, end);
    }

    /**
     * 获取某个代理商下所有用户的当期下注统计信息
     */
    @RequestMapping(value = "user/wage_summary/groupid/{groupid}", method = RequestMethod.GET)
    public @ResponseBody List<LotteryMarkSixUserStat> getUserWageSummary(@PathVariable("groupid") String groupid) {
        return adminReportService.getUserWageReport(groupid);
    }

}
