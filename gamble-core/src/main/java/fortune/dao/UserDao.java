package fortune.dao;

import fortune.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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
        update.set("lastLoginTime", new Date());
        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), User.class);

    }

    public boolean depositAccount(String userid, double account) {
        try {
            User user = getUserById(userid);
            Query query = new Query(Criteria.where("id").is(userid));
            Update update = new Update();
            update.set("account", user.getAccount() + account);
            mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), User.class);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
