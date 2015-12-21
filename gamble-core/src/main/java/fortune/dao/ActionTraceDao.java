package fortune.dao;

import fortune.pojo.ActionTrace;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by tangl9 on 2015-12-21.
 */
@Repository
public class ActionTraceDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(ActionTrace actionTrace) {
        sessionFactory.getCurrentSession().save(actionTrace);
    }
}
