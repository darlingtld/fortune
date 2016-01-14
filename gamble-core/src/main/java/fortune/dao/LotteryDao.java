package fortune.dao;

import fortune.pojo.LotteryMarkSix;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tangl9 on 2015-10-14.
 */
@Repository
public class LotteryDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<LotteryMarkSix> getLotteryMarkSixAll() {
        return sessionFactory.getCurrentSession().createQuery(String.format("from LotteryMarkSix order by timestamp desc")).list();
    }

    public void saveLotteryMarkSix(LotteryMarkSix lotteryMarkSix) {
        sessionFactory.getCurrentSession().save(lotteryMarkSix);
    }

    public LotteryMarkSix getLotteryMarkSix(int lotteryIssue) {
        return (LotteryMarkSix) sessionFactory.getCurrentSession().createQuery(String.format("from LotteryMarkSix where issue = %d", lotteryIssue)).uniqueResult();
    }

    public int getLatestLotteryIssue() {
        Object issue = sessionFactory.getCurrentSession().createQuery(String.format("select issue from LotteryMarkSix order by issue desc")).setMaxResults(1).uniqueResult();
        return issue == null ? 1 : (int) issue;
    }

    public List<LotteryMarkSix> getLotteryMarkSixByPagination(int from, int count) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from LotteryMarkSix order by issue desc")).setFirstResult(from).setMaxResults(count).list();
    }
    
    public List<LotteryMarkSix> getLotteryMarkSixByTimeRange(String start, String end) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from LotteryMarkSix where timestamp >= '%s' and timestamp <= '%s'", start, end)).list();
    }

    public int getLotteryMarkSixCount() {
        return ((Number) sessionFactory.getCurrentSession().createQuery(String.format("select count(*) from LotteryMarkSix")).uniqueResult()).intValue();
    }
}
