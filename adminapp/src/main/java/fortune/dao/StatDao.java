package fortune.dao;

import fortune.pojo.LotteryMarkSixGroupStat;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    
    public List<LotteryMarkSixGroupStat> getStatsOfAllSubGroup(String groupid, String start, String end) {
        //FIXME 查找所有属于该group的所有下一级group的统计信息
        return new ArrayList<>();
    }

    public void saveLotteryMarkSixStat(LotteryMarkSixGroupStat stat) {
        sessionFactory.getCurrentSession().save(stat);
    }
}
