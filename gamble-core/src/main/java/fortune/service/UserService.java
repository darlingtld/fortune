package fortune.service;

import com.google.common.base.Strings;
import common.Utils;
import fortune.dao.UserDao;
import fortune.pojo.Role;
import fortune.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import password.PasswordEncryptUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by darlingtld on 2015/10/6 0006.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public User getUserById(String id) {
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
                user.setLastLoginTime(new Date());
                userDao.updateUser(user);
                return user;
            }
        }
    }

    @Transactional
    public User getUserByUsername(String name) {
        Utils.logger.info("get user by name {}", name);
        return userDao.getUserByUsername(name);
    }

    @Transactional
    public void register(String name, String password) {
        Utils.logger.info("register user {} {}", name, password);
        if (Strings.isNullOrEmpty(name)) {
            throw new RuntimeException("账号不能为空！");
        } else if (Strings.isNullOrEmpty(password)) {
            throw new RuntimeException("密码不能为空！");
        } else if (userDao.getUserByUsername(name) != null) {
            throw new RuntimeException("该用户名已存在！");
        } else {
            User user = new User();
            user.setUsername(name);
            user.setPassword(PasswordEncryptUtil.encrypt(password));
            user.setRoleList(Arrays.asList(Role.NORMAL_USER));
            userDao.createUser(user);
        }
    }

    @Transactional
    public boolean depositAccount(String userid, double account) {
        Utils.logger.info("deposit account {} for user id {}", account, userid);
        return userDao.depositAccount(userid, account);
    }

    @Transactional
    public User adminLogin(String username, String password) {
        Utils.logger.info("admin user login [name:{}, password:{}]", username, password);
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            Utils.logger.info("user name not existed");
            return null;
        } else {
            if (!PasswordEncryptUtil.matches(password, user.getPassword())) {
                Utils.logger.info("password does not match");
                return null;
            } else {
                if (!user.getRoleList().contains(Role.GROUP_ADMIN)) {
                    Utils.logger.info("roles does not match");
                    return null;
                } else {
                    user.setLastLoginTime(new Date());
                    userDao.updateUser(user);
                    return user;
                }
            }
        }
    }
}
