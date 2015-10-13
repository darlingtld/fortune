package fortune.service;

import fortune.dao.UserDao;
import fortune.pojo.User;
import fortune.utililty.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import password.PasswordEncryptUtil;

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
        Utils.logger.info("create user {}", user);
//        check user if existed
        if (getUserByUsername(user.getUsername()) != null) {
            throw new RuntimeException("username already existed");
        }
        user.setPassword(PasswordEncryptUtil.encrypt(user.getPassword()));
        userDao.createUser(user);
    }

    @Transactional
    public List<User> getAll() {
        Utils.logger.info("get all users");
        return userDao.getAll();
    }

    @Transactional
    public User login(String name, String password) {
        Utils.logger.info("user login [name:{}, password:{}]", name, password);
        User user = userDao.getUserByUsername(name);
        if (user == null) {
            Utils.logger.info("user name not existed");
            return null;
        } else {
            if (!PasswordEncryptUtil.matches(password, user.getPassword())) {
                Utils.logger.info("password does not match");
                return null;
            } else {
                return user;
            }
        }
    }

    @Transactional
    public User getUserByUsername(String name) {
        Utils.logger.info("get user by name {}", name);
        return userDao.getUserByUsername(name);
    }
}
