package fortune.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.Utils;
import fortune.dao.PGroupDao;
import fortune.dao.StatDao;
import fortune.pojo.LotteryMarkSixGroupStat;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryMarkSixUserStat;
import fortune.pojo.PGroup;
import fortune.pojo.User;

@Service
public class AdminReportService {
    
    @Autowired
    StatDao statDao;
    
    @Autowired
    PGroupDao groupDao;

    @Transactional
    public List<LotteryMarkSixGroupStat> getLotteryMarkSixStat(String groupid, int from, int count) {
        Utils.logger.info("get lottery mark six stat for group id {} from {}, count {}", groupid, from, count);
        return statDao.getLotteryMarkSixStat(groupid, from, count);
    }
    
    @Transactional
    public List<LotteryMarkSixGroupStat> getSubGroupSummaryByDateRange(String groupid, String start, String end) {
        Utils.logger.info("get stat summary for group id {} from {} to {}", groupid, start, end);
        List<LotteryMarkSixGroupStat> resultList = new ArrayList<>();
        
        List<PGroup> groupList = groupDao.getPGroupsByParentID(groupid);
        for (PGroup subGroup : groupList) {
            List<LotteryMarkSixGroupStat> statList = statDao.getStatSummaryOfGroup(subGroup.getId(), start, end);

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
            sumStat.setPgroupId(subGroup.getId());
            sumStat.setTotalStakes(totalStakes);
            sumStat.setUserResult(userResult);
            sumStat.setPgroupResult(pgroupResult);
            sumStat.setZoufeiStakes(zoufeiStakes);
            sumStat.setZoufeiResult(zoufeiResult);
            sumStat.setPgroupTotalResult(pgroupTotalResult);
            sumStat.setPgroup(subGroup);  // transient
            resultList.add(sumStat);
        }
        
        return resultList;
    }
    
    @Transactional
    public List<LotteryMarkSixGroupStat> getSummary4AllType(String groupid, String start, String end) {
        Utils.logger.info("get stat summary for sub type for group id {} from {} to {}", groupid, start, end);
        //FIXME 获取某个group在该时间范围内的所有类型的注单统计信息
        
        return null;
    }
    
    @Transactional
    public List<LotteryMarkSixGroupStat> getSummary4Type(String groupid, LotteryMarkSixType type, String start, String end) {
        Utils.logger.info("get stat summary for sub type for group id {} from {} to {}", groupid, start, end);
        //FIXME 获取某个group在该时间范围内的某个类型的注单统计信息
        
        return null;
    }
    
    @Transactional
    public List<LotteryMarkSixUserStat> getUserSummaryByDateRange(String groupid, String start, String end) {
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
