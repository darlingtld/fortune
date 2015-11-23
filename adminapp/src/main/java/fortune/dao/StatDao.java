package fortune.dao;

import fortune.pojo.LotteryMarkSixGroupStat;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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


    public void saveLotteryMarkSixStat(LotteryMarkSixGroupStat stat) {
        sessionFactory.getCurrentSession().save(stat);
    }
}
