package fortune.dao;

import common.Utils;
import fortune.pojo.JobTracker;
import fortune.pojo.LotteryDrawTracker;
import fortune.pojo.LotteryMarkSixType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by tangl9 on 2015-11-23.
 */
@Repository
public class JobTrackerDao {
    @Autowired
    private SessionFactory sessionFactory;

    public int save(JobTracker jobTracker) {
        return ((Number) sessionFactory.getCurrentSession().save(jobTracker)).intValue();
    }

    public void updateEndStatus(int id, Date endTime, String status) {
        sessionFactory.getCurrentSession().createQuery(String.format("update JobTracker set endTime='%s', status='%s' where id=%d", Utils.yyyyMMddHHmmss2Format(endTime), status, id)).executeUpdate();
    }

    public JobTracker getById(int id) {
        return (JobTracker) sessionFactory.getCurrentSession().get(JobTracker.class, id);
    }

    public JobTracker getJobByNameAndIssue(LotteryMarkSixType lotteryMarkSixType, int lotteryIssue) {
        return (JobTracker) sessionFactory.getCurrentSession().createQuery(String.format("from JobTracker where name='%s' and issue=%d", lotteryMarkSixType, lotteryIssue)).uniqueResult();
    }

    public void saveLotteryDrawTracker(LotteryDrawTracker lotteryDrawTracker) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery(String.format("delete LotteryDrawTracker")).executeUpdate();
        session.save(lotteryDrawTracker);
    }

    public LotteryDrawTracker getLotteryDrawTracker() {
        return (LotteryDrawTracker) sessionFactory.getCurrentSession().createQuery(String.format("from LotteryDrawTracker order by lastLotteryIssue desc")).setMaxResults(1).uniqueResult();
    }
}
