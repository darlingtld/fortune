package fortune.controller;

import fortune.pojo.LotteryMarkSixUserStat;
import fortune.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by tangl9 on 2015-11-23.
 */
@Scope("prototype")
@Controller
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private StatService statService;

    @RequestMapping(value = "userid/{userid}/from/{startdate}/to/{enddate}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryMarkSixUserStat> getReportList(@PathVariable("userid") String userId, @PathVariable("startdate") Date startDate, @PathVariable("enddate") Date endDate) {
        return statService.getLotteryMarkSixUserStatList(userId, startDate, endDate);
    }

}
