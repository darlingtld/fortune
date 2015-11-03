package fortune.dao;

import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixStat;
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


    public List<LotteryMarkSixStat> getLotteryMarkSixStat(String groupid, int from, int count) {
        Query query = sessionFactory.getCurrentSession().createQuery(String.format("from LotteryMarkSixStat where pgroupId = '%s' order by lotteryMarkSix.issue desc", groupid));
        query.setFirstResult(from);
        query.setMaxResults(count);
        return query.list();
    }
}
