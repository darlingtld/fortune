package fortune.dao;

import fortune.pojo.PGroup;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by tangl9 on 2015-10-13.
 */
@Repository
public class PGroupDao {

    @Autowired
    private SessionFactory sessionFactory;

    public PGroup getGroupById(int id) {
        return (PGroup) sessionFactory.getCurrentSession().get(PGroup.class, id);
    }

    public void createGroup(PGroup PGroup) {
        sessionFactory.getCurrentSession().save(PGroup);
    }
}
