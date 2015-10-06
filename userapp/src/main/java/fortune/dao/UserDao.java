package fortune.dao;

import fortune.pojo.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by darlingtld on 2015/10/6 0006.
 */
@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public User getUserById(int id){
        return (User) sessionFactory.getCurrentSession().get(User.class,id);
    }

}
