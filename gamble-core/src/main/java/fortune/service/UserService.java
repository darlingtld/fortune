package fortune.service;

import java.util.Date;
import java.util.List;

import fortune.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.Utils;
import fortune.dao.PGroupDao;
import fortune.dao.UserDao;
import password.PasswordEncryptUtil;

/**
 * Created by darlingtld on 2015/10/6 0006.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PGroupDao pGroupDao;

    @Transactional
    public User getUserById(String id) {
        Utils.logger.info("get user by id {}", id);
        return userDao.getUserById(id);
    }

    @Transactional
    public void createUser(User user) {
        Utils.logger.info("create user {}", user);
        // check user if existed
        if (getUserByUsername(user.getUsername()) != null) {
            throw new RuntimeException("username already existed");
        }
        user.setPassword(PasswordEncryptUtil.encrypt(user.getPassword()));
        userDao.createUser(user);
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
                if (!user.getRoleList().contains(Role.NORMAL_USER)) {
                    Utils.logger.info("roles does not match");
                    return null;
                } else if (user.getStatus().equals(PeopleStatus.DISABLED)) {
                    Utils.logger.info("user is disabled");
                    return null;
                } else {
                    user.setLastLoginTime(new Date());
                    userDao.updateUser(user);
                    userDao.updateLastLoginTime(user);
                    return user;
                }
            }
        }
    }

    @Transactional
    public User getUserByUsername(String name) {
        Utils.logger.info("get user by name {}", name);
        return userDao.getUserByUsername(name);
    }

    @Transactional
    public boolean depositAccount(String userid, double account) {
        Utils.logger.info("deposit account {} for user id {}", account, userid);
        return userDao.depositAccount(userid, account);
    }

    @Transactional
    public PGroup adminLogin(String username, String password) {
        Utils.logger.info("admin user login [name:{}, password:{}]", username, password);
        PGroup pGroup = pGroupDao.getGroupByAdminUserName(username);
        if (pGroup == null) {
            Utils.logger.info("user name not existed");
            throw new RuntimeException("user name not existed");
        } else {
            if (!PasswordEncryptUtil.matches(password, pGroup.getAdmin().getPassword())) {
                Utils.logger.info("password does not match");
                throw new RuntimeException("user name not existed");
            } else {
                return pGroup;
            }
        }
    }

    @Transactional
    public List<User> getAll() {
        Utils.logger.info("get all users");
        return userDao.getAll();
    }

    @Transactional
    public void updateAccount(User user) {
        Utils.logger.info("update account of user {}, creditAccount {}, usedCreditAccount {}", user.getUsername(),
                user.getCreditAccount(), user.getUsedCreditAccount());
        userDao.updateAccount(user);
    }

    @Transactional
    public void updateUserCreditByID(String userId, double creditValue) {
        User user = userDao.getUserById(userId);
        user.setCreditAccount(creditValue);
        userDao.updateAccount(user);
    }

    public void sanitize(User user) {
        user.setpGroupList(null);
    }

    @Transactional
    public User changePass(User user, String newPass) {
        user.setPassword(PasswordEncryptUtil.encrypt(newPass));
        return userDao.updatePassword(user);
    }

    @Transactional
    public void switchZoufeiStatus(String groupId, String userId) {
        userDao.switchZoufeiStatus(groupId, userId);
    }

    @Transactional
    public ZoufeiSetting getZoufeiByUserId(String pGroupId, String userId) {
        User user = getUserById(userId);
        ZoufeiStub zoufeiStub = user.getZoufeiEnabledMap().get(pGroupId);
        ZoufeiSetting zoufeiSetting = new ZoufeiSetting();
        if (zoufeiStub == null) {
            zoufeiStub = new ZoufeiStub();
        }
        zoufeiSetting.setStatus(zoufeiStub.isZoufeiAutoEnabled ? "自动走飞" : "手动走飞");
        zoufeiSetting.setCurStake(zoufeiStub.thresholdStakes);
        zoufeiSetting.setMaxStake(zoufeiStub.thresholdStakes);
        return zoufeiSetting;

    }

    @Transactional
    public void saveZoufeiSetting(String pGroupId, String userId, ZoufeiSetting zoufeiSetting) {
        userDao.saveZoufeiSetting(pGroupId, userId, zoufeiSetting);
    }

    public boolean isUserZoufeiAutoEnabled(User user, String groupId) {
        if (user.getZoufeiEnabledMap() != null && !user.getZoufeiEnabledMap().getOrDefault(groupId, new ZoufeiStub()).isZoufeiAutoEnabled) {
            return false;
        } else {
            return true;
        }
    }
}
