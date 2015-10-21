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

}
