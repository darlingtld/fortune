package fortune.service;

import fortune.dao.UserDao;
import fortune.pojo.User;
import fortune.utililty.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by darlingtld on 2015/10/6 0006.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public User getUserById(int id) {
        Utils.logger.info("get user by id {}", id);
        return userDao.getUserById(id);
    }

    @Transactional
    public void createUser(User user) {
        Utils.logger.info("crate user {}", user);
        userDao.createUser(user);
    }

    @Transactional
    public List<User> getAll() {
        Utils.logger.info("get all users");
        return userDao.getAll();
    }
}
