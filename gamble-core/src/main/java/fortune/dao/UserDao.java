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

    public User getUserByUsername(String username) {
        return (User) sessionFactory.getCurrentSession().createQuery(String.format("from User where username='%s'", username)).uniqueResult();
    }

    public void updateUser(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    public boolean depositAccount(int userid, double account) {
        try {
            sessionFactory.getCurrentSession().createSQLQuery(String.format("update User set account = account+%s where id = %d", account, userid)).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
