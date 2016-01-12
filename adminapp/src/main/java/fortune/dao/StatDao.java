package fortune.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fortune.pojo.LotteryMarkSixGroupStat;
import fortune.pojo.LotteryMarkSixUserStat;

/**
 * Created by tangl9 on 2015-11-03.
 */
@Repository
public class StatDao {

    @Autowired
    private SessionFactory sessionFactory;
    
    public List<LotteryMarkSixGroupStat> getLotteryMarkSixStat(String groupid, int from, int count) {
        Query query = sessionFactory.getCurrentSession().createQuery(String.format("from LotteryMarkSixGroupStat where pgroupId = '%s' order by lotteryMarkSix.issue desc", groupid));
        query.setFirstResult(from);
        query.setMaxResults(count);
        return query.list();
    }
    
    public List<LotteryMarkSixGroupStat> getStatSummaryOfGroup(String groupid, String start, String end) {
        String sql = "from LotteryMarkSixGroupStat stat where pgroupId = '%s' and stat.lotteryMarkSix.timestamp >= '%s' and stat.lotteryMarkSix.timestamp <= '%s'";
        Query query = sessionFactory.getCurrentSession().createQuery(String.format(sql, groupid, start, end));
        return query.list();
    }
    
    public List<LotteryMarkSixUserStat> getLotteryMarkSixUserStatList(String userId, String start, String end) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from LotteryMarkSixUserStat where userId='%s' and openTs >= '%s' and openTs <='%s' order by issue desc", userId, start, end)).list();
    }

    public void saveLotteryMarkSixStat(LotteryMarkSixGroupStat stat) {
        sessionFactory.getCurrentSession().save(stat);
    }
}
