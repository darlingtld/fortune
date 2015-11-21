package fortune.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import fortune.pojo.PGroup;
import fortune.pojo.User;
import fortune.service.UserService;

/**
 * Created by tangl9 on 2015-10-13.
 */
@Repository
public class PGroupDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private UserService userService;

	public PGroup getGroupById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, PGroup.class);
	}

	public void createGroup(PGroup pGroup) {
		mongoTemplate.save(pGroup);
	}

	public List<PGroup> getGroupAll() {
		return mongoTemplate.findAll(PGroup.class);
	}

	public PGroup updatePGroup(PGroup pGroup) {
		Query query = new Query(Criteria.where("id").is(pGroup.getId()));
		Update update = new Update();
		update.set("name", pGroup.getName());
		update.set("userList", pGroup.getUserList());
		return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), PGroup.class);
	}

	public PGroup getGroupByAdminUserName(String username) {
		Query query = new Query(Criteria.where("admin.username").is(username));
		return mongoTemplate.findOne(query, PGroup.class);
	}

	public PGroup changeAdmin(PGroup pgroup, User user) {
		Query query = new Query(Criteria.where("id").is(pgroup.getId()));
		Update update = new Update();
		userService.sanitize(user);
		update.set("admin", user);
		return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), PGroup.class);
	}

	public PGroup getPGroupByName(String name) {
		Query query = new Query(Criteria.where("name").is(name));
		return mongoTemplate.findOne(query, PGroup.class);
	}

	public List<PGroup> getPGroupsByParentID(String parentId) {
		Query query = new Query(Criteria.where("parentPGroupID").is(parentId));
		return mongoTemplate.find(query, PGroup.class);
	}

	public void deletePGroupByID(String pgroupId) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(pgroupId)), PGroup.class);
	}
}
