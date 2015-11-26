package fortune.job;

import fortune.service.JobTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tangl9 on 2015-11-25.
 */
@Service
public class JobService {

    @Autowired
    private JobTrackerService jobTrackerService;

    public boolean hasJobRun(String jobName, int lotteryIssue) {
        return jobTrackerService.getJobByNameAndIssue(jobName, lotteryIssue) != null;
    }

    public boolean canGroupStatJobStart(int lotteryIssue) {
        return jobTrackerService.hasAllLotteryResultJobsFinished(lotteryIssue);
    }

    public boolean canUserStatJobStart(int lotteryIssue) {
        return jobTrackerService.hasAllLotteryResultJobsFinished(lotteryIssue);
    }
}
