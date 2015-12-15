package fortune.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.Utils;
import fortune.dao.PGroupDao;
import fortune.dao.UserDao;
import fortune.pojo.PGroup;
import fortune.pojo.Role;
import fortune.pojo.User;
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
				} else {
					user.setLastLoginTime(new Date());
					userDao.updateUser(user);
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
			return null;
		} else {
			if (!PasswordEncryptUtil.matches(password, pGroup.getAdmin().getPassword())) {
				Utils.logger.info("password does not match");
				return null;
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
}
