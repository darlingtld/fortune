package fortune.service;

import fortune.dao.JobTrackerDao;
import fortune.pojo.JobTracker;
import fortune.pojo.LotteryMarkSixType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by tangl9 on 2015-11-23.
 */
@Service
public class JobTrackerService {
    @Autowired
    private JobTrackerDao jobTrackerDao;

    @Transactional
    public int save(JobTracker jobTracker) {
        return jobTrackerDao.save(jobTracker);
    }

    @Transactional
    public void updateEndStatus(int id, Date endTime, String status) {
        jobTrackerDao.updateEndStatus(id, endTime, status);
    }

    @Transactional
    public JobTracker getById(int id) {
        return jobTrackerDao.getById(id);
    }

    @Transactional
    public JobTracker getJobByNameAndIssue(LotteryMarkSixType lotteryMarkSixType, int lotteryIssue) {
        return jobTrackerDao.getJobByNameAndIssue(lotteryMarkSixType, lotteryIssue);
    }
}