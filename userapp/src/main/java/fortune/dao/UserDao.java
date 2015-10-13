package fortune.dao;

import fortune.pojo.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by darlingtld on 2015/10/6 0006.
 */
@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public User getUserById(int id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    public void createUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @SuppressWarnings("JpaQlInspection")
    public List<User> getAll() {
        return sessionFactory.getCurrentSession().createQuery("from User").list();
    }
}
