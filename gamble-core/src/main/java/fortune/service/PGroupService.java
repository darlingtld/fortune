package fortune.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.Utils;
import fortune.dao.PGroupDao;
import fortune.dao.UserDao;
import fortune.pojo.PGroup;
import fortune.pojo.User;
import password.PasswordEncryptUtil;

/**
 * Created by tangl9 on 2015-10-13.
 */
@Service
public class PGroupService {

	@Autowired
	private PGroupDao pGroupDao;

	@Autowired
	private UserDao userDao;

	@Transactional
	public PGroup getGroupById(String id) {
		Utils.logger.info("get pGroup by id {}", id);
		return pGroupDao.getGroupById(id);
	}

	@Transactional
	public void createGroup(PGroup pGroup) {
		Utils.logger.info("create pGroup {}", pGroup);
		pGroupDao.createGroup(pGroup);
	}

	@Transactional
	public List<PGroup> getGroupAll() {
		Utils.logger.info("get pGroup all");
		return pGroupDao.getGroupAll();
	}

	@Transactional
	public void addUser(String pGroupId, User user) {
		Utils.logger.info("pgroup {} add user {}", pGroupId, user);
		PGroup pGroup = pGroupDao.getGroupById(pGroupId);
		List<User> userList = pGroup.getUserList();
		pGroup.setUserList(null);
		// 如果存在该用户就不用插入了
		User existedUser=userDao.getUserByUsername(user.getUsername());
		if(existedUser==null){
			user.setpGroupList(Arrays.asList(pGroup));
			user.setPassword(PasswordEncryptUtil.encrypt(user.getPassword()));
			userDao.createUser(user);
		}
		// 更新用户的pgroup列表
		else{
			List<PGroup> pgroupList=existedUser.getpGroupList();
			pgroupList.add(pGroup);
			userDao.updateUser(existedUser);
		}
		// 更新pgroup的userList
		user = userDao.getUserByUsername(user.getUsername());
		boolean isUpdate = false;
		if (userList == null) {
			userList = new ArrayList<User>();
			isUpdate = true;
		} else if (!isUserInUserList(user, userList)) {
			isUpdate = true;
		}
		if (isUpdate) {
			userList.add(user);
			pGroup.setUserList(userList);
			pGroupDao.updatePGroup(pGroup);
		}
	}

	private boolean isUserInUserList(User user, List<User> userList) {
		for (User u : userList) {
			if (user.getId().equals(u.getId())) {
				return true;
			}
		}
		return false;
	}

	@Transactional
	public PGroup changeAdmin(PGroup pgroup, User user) {
		Utils.logger.info("change pgroup {} admin to {}", pgroup.getName(), user.getUsername());
		if (isAdminBelongsToOtherPgroup(user)) {
			throw new RuntimeException(String.format("user has already been assigned the admin to other pgroups"));
		} else {
			return pGroupDao.changeAdmin(pgroup, user);
		}
	}

	@Transactional
	private boolean isAdminBelongsToOtherPgroup(User admin) {
		PGroup pGroup = pGroupDao.getGroupByAdminUserName(admin.getUsername());
		return pGroup != null;
	}

	@Transactional
	public PGroup getPGroupByName(String name) {
		return pGroupDao.getPGroupByName(name);

	}

	public List<PGroup> getPGroupsByParentID(String parentId) {
		return pGroupDao.getPGroupsByParentID(parentId);
	}

	public List<User> getUsersByPGroupID(String pgroupId) {
		PGroup pGroup = pGroupDao.getGroupById(pgroupId);
		return pGroup.getUserList();
	}

	public void deletePGroupByID(String pgroupId) {
		PGroup pGroup = pGroupDao.getGroupById(pgroupId);
		// 当该代理商下没有用户，并且下面没有代理商，才能删除
		if(pGroup.getUserList().size()>0 || pGroupDao.getPGroupsByParentID(pgroupId).size()>0){
			return;
		}
		pGroupDao.deletePGroupByID(pgroupId);
	}

	public void deleteUserByID(String userId) {
		//需要删除代理商下的userList
	}
}
