package fortune.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import fortune.pojo.PeopleStatus;
import fortune.pojo.User;

/**
 * Created by darlingtld on 2015/10/6 0006.
 */
@Repository
public class UserDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	public User getUserById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, User.class);
	}

	public void createUser(User user) {
		mongoTemplate.save(user);
	}

	public List<User> getAll() {
		return mongoTemplate.findAll(User.class);
	}

	public User getUserByUsername(String username) {
		Query query = new Query(Criteria.where("username").is(username));
		return mongoTemplate.findOne(query, User.class);
	}

	public User updateUser(User user) {
		Query query = new Query(Criteria.where("id").is(user.getId()));
		Update update = new Update();
		update.set("roleList", user.getRoleList());
		update.set("pGroupList", user.getpGroupList());
		return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), User.class);

	}

	public User updateLastLoginTime(User user) {
		Query query = new Query(Criteria.where("id").is(user.getId()));
		Update update = new Update();
		update.set("lastLoginTime", new Date());
		return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), User.class);

	}

	public boolean depositAccount(String userid, double account) {
		try {
			User user = getUserById(userid);
			Query query = new Query(Criteria.where("id").is(userid));
			Update update = new Update();
			update.set("creditAccount", user.getCreditAccount() + account);
			mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), User.class);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public User updateAccount(User user) {
		Query query = new Query(Criteria.where("id").is(user.getId()));
		Update update = new Update();
		update.set("creditAccount", user.getCreditAccount());
		update.set("usedCreditAccount", user.getUsedCreditAccount());
		return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), User.class);
	}

	public void deleteUserByID(String userId) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(userId)), User.class);
	}
	
	public void updateUserStatusByID(String userId, PeopleStatus status){
		Query query = new Query(Criteria.where("id").is(userId));
		Update update = new Update();
		update.set("status", status);
		mongoTemplate.updateFirst(query, update, User.class);
	}

	public void deleteUserByUserName(String username) {
		mongoTemplate.remove(new Query(Criteria.where("username").is(username)), User.class);
	}

	public User updatePassword(User user) {
		Query query = new Query(Criteria.where("id").is(user.getId()));
		Update update = new Update();
		update.set("password", new Date());
		return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), User.class);
	}

	public void switchZoufeiStatus(String userId) {
		User user = getUserById(userId);
		Query query = new Query(Criteria.where("id").is(user.getId()));
		Update update = new Update();
		update.set("isZoufeiAutoEnabled", !user.isZoufeiAutoEnabled());
		mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), User.class);
	}
}
