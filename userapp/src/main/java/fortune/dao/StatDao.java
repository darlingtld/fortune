package fortune.dao;

import fortune.pojo.LotteryMarkSixUserStat;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by tangl9 on 2015-11-23.
 */
@Repository
public class StatDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<LotteryMarkSixUserStat> getLotteryMarkSixUserStatList(String userId, Date starDate, Date endDate) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from LotteryMarkSixUserStat where userId='%s' and openTs >= '%s' and openTs <='%s'", userId, starDate, endDate)).list();
    }

    public void saveLotteryMarkSixStat(LotteryMarkSixUserStat stat) {
        sessionFactory.getCurrentSession().save(stat);
    }
}
