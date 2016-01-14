package fortune.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.Utils;
import fortune.dao.LotteryDao;
import fortune.dao.LotteryResultDao;
import fortune.dao.PGroupDao;
import fortune.dao.StatDao;
import fortune.pojo.AdminReport;
import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixGroupStat;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryMarkSixUserStat;
import fortune.pojo.LotteryMarkSixWager;
import fortune.pojo.LotteryResult;
import fortune.pojo.PGroup;
import fortune.pojo.User;
import fortune.util.CommonUtils;

@Service
public class AdminReportService {
    
    @Autowired
    StatDao statDao;
    
    @Autowired
    PGroupDao groupDao;
    
    @Autowired
    WagerService wagerService;
    
    @Autowired
    LotteryResultDao lotteryResultDao;
    
    @Autowired
    LotteryDao lotteryDao;
    
    @Autowired
    ResultService resultService;

    @Transactional
    public List<LotteryMarkSixGroupStat> getLotteryMarkSixStat(String groupid, int from, int count) {
        Utils.logger.info("get lottery mark six stat for group id {} from {}, count {}", groupid, from, count);
        return statDao.getLotteryMarkSixStat(groupid, from, count);
    }
    
    /**
     * 获取某个时间段内某个代理商下各个子代理商的统计信息
     * @param groupid
     * @param start
     * @param end
     * @return
     */
    @Transactional
    public List<LotteryMarkSixGroupStat> getSubGroupReportByDateRange(String groupid, String start, String end) {
        Utils.logger.info("get report for all sub groups of group id {} from {} to {}", groupid, start, end);
        List<LotteryMarkSixGroupStat> resultList = new ArrayList<>();
        
        List<PGroup> groupList = groupDao.getPGroupsByParentID(groupid);
        for (PGroup subGroup : groupList) {
            LotteryMarkSixGroupStat sumStat = getGroupReportByDateRange(subGroup.getId(), start, end);
            sumStat.setPgroup(subGroup);  // transient, used in front end
            resultList.add(sumStat);
        }
        
        return resultList;
    }
    
    /**
     * 获取某个时间段内某个代理商的统计信息
     * @param groupid
     * @param start
     * @param end
     * @return
     */
    @Transactional
    public LotteryMarkSixGroupStat getGroupReportByDateRange(String groupid, String start, String end) {
        Utils.logger.info("get report for group id {} from {} to {}", groupid, start, end);
        
        List<LotteryMarkSixGroupStat> statList = statDao.getStatSummaryOfGroup(groupid, start, end);
        
        double totalStakes = 0.0;
        double userResult = 0.0;
        double pgroupResult = 0.0;
        double zoufeiStakes = 0.0;
        double zoufeiResult = 0.0;
        double pgroupTotalResult = 0.0;
        for (LotteryMarkSixGroupStat stat : statList) {
            totalStakes += stat.getTotalStakes();
            userResult += stat.getUserResult();
            pgroupResult += stat.getPgroupResult();
            zoufeiStakes += stat.getZoufeiStakes();
            zoufeiResult += stat.getZoufeiResult();
            pgroupTotalResult += stat.getPgroupTotalResult();
        }
        
        LotteryMarkSixGroupStat sumStat = new LotteryMarkSixGroupStat();
        sumStat.setPgroupId(groupid);
        sumStat.setTotalStakes(totalStakes);
        sumStat.setUserResult(userResult);
        sumStat.setPgroupResult(pgroupResult);
        sumStat.setZoufeiStakes(zoufeiStakes);
        sumStat.setZoufeiResult(zoufeiResult);
        sumStat.setPgroupTotalResult(pgroupTotalResult);
    
        return sumStat;
    }
    
    /**
     * 获取某个时间段所有注单类型的报表信息
     * @param groupid
     * @param start
     * @param end
     * @return
     */
    @Transactional
    public List<AdminReport> getReport4AllType(String groupid, String start, String end) {
        List<AdminReport> reportList = new ArrayList<>();
        LotteryMarkSixType.getWagerTypeList().stream().forEach(type -> {
            reportList.add(getReport4Type(type.name(), groupid, start, end));
        });
        return reportList;
    }
    
    /**
     * 获取某个时间段内某种注单类型的报表信息
     * @param type
     * @param groupid
     * @param start
     * @param end
     * @return
     */
    @Transactional
    public AdminReport getReport4Type(String type, String groupid, String start, String end) {
        Utils.logger.info("get admin report for type {} for group id {} from {} to {}", type, groupid, start, end);
        
        int totalTransactions = 0;
        double totalStakes = 0.0;
        double totalUserResult = 0.0;
        
        LotteryMarkSixType wagerType = LotteryMarkSixType.valueOf(type);
        
        List<LotteryMarkSix> lotteryList = lotteryDao.getLotteryMarkSixByTimeRange(start, end);
        for (LotteryMarkSix lottery : lotteryList) {
            int issue = lottery.getIssue();
            
            List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(wagerType, groupid, "ALL", issue, null);
            for (LotteryMarkSixWager wager : wagerList) {
                totalTransactions += CommonUtils.getTransactionsOfWager(wager);
                totalStakes += wager.getTotalStakes();
            }
            
            LotteryResult userResult = lotteryResultDao.calSumResult4WagerList(wagerList);
            totalUserResult += userResult != null ? userResult.getWinningMoney() : 0;
        }
        
        PGroup group = groupDao.getGroupById(groupid);
        
        AdminReport report = new AdminReport();
        report.setPgroupId(groupid);
        report.setPgroup(group);
        report.setTotalTransactions(totalTransactions);
        report.setTotalStakes(totalStakes);
        report.setUserResult(totalUserResult);
        report.setWagerType(wagerType);
        report.setWagerTypeName(wagerType.getType());
        
        return report;
    }
    
    /**
     * 获取某个时间段内某代理商的直属会员的报表信息
     * @param groupid
     * @param start
     * @param end
     * @return
     */
    @Transactional
    public List<LotteryMarkSixUserStat> getUserReportByDateRange(String groupid, String start, String end) {
        Utils.logger.info("get user summary for group id {} from {} to {}", groupid, start, end);
        List<LotteryMarkSixUserStat> resultList = new ArrayList<>();
        
        PGroup group = groupDao.getGroupById(groupid);
        for (User user : group.getUserList()) {
            List<LotteryMarkSixUserStat> statList = statDao.getLotteryMarkSixUserStatList(user.getId(), start, end);

            double totalStakes = 0.0;
            double totalValidStakes = 0.0;
            double totalUserResult = 0.0;
            double totalTuishui = 0.0;
            for (LotteryMarkSixUserStat stat : statList) {
                totalStakes += stat.getStakes();
                totalValidStakes += stat.getValidStakes();
                totalUserResult += stat.getResult();
                totalTuishui += stat.getTuishui();
            }
            
            LotteryMarkSixUserStat sumStat = new LotteryMarkSixUserStat();
            sumStat.setUser(user);
            sumStat.setUserId(user.getId());
            sumStat.setStakes(totalStakes);
            sumStat.setValidStakes(totalValidStakes);
            sumStat.setResult(totalUserResult);
            sumStat.setTuishui(totalTuishui);
            resultList.add(sumStat);
        }
        
        return resultList;
    }
}
