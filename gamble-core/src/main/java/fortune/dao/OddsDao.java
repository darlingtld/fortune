package fortune.dao;

import fortune.pojo.Odds;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tangl9 on 2015-10-16.
 */
@Repository
public class OddsDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Odds getOddsById(int oddsId) {
        return (Odds) sessionFactory.getCurrentSession().get(Odds.class, oddsId);
    }

    public void saveOdds(Odds odds) {
        sessionFactory.getCurrentSession().save(odds);
    }

    public List<Odds> getAll() {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Odds")).list();
    }

    public List<Odds> getOdds4Lottery(int lotteryId) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Odds where lotteryMarkSix.id=%d", lotteryId)).list();
    }
}
