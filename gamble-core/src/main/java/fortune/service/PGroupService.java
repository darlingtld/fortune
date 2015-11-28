package fortune.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;

import common.Utils;
import fortune.dao.PGroupDao;
import fortune.dao.UserDao;
import fortune.pojo.PGroup;
import fortune.pojo.PeopleStatus;
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
	public boolean createGroup(PGroup pGroup) {
		Utils.logger.info("create pGroup {}", pGroup);
		// 插入管理员
		User admin = pGroup.getAdmin();
		admin.setPassword(PasswordEncryptUtil.encrypt(admin.getPassword()));
		// 一个管理员只能管理一个pgroup
		PGroup adminGroup = pGroupDao.getGroupByAdminUserName(admin.getUsername());
		User existedUser = userDao.getUserByUsername(admin.getUsername());
		if (adminGroup == null) {
			if (existedUser == null) {
				userDao.createUser(admin);
			}
			// 插入pgroup
			pGroup.setAdmin(userDao.getUserByUsername(admin.getUsername()));
			pGroupDao.createGroup(pGroup);
			return true;
		} else {
			return false;
		}
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
		User existedUser = userDao.getUserByUsername(user.getUsername());
		if (existedUser == null) {
			user.setpGroupList(Arrays.asList(pGroup));
			user.setPassword(PasswordEncryptUtil.encrypt(user.getPassword()));
			userDao.createUser(user);
		}
		// 更新用户的pgroup列表
		else if (!isUserInUserList(user, userList)) {
			List<PGroup> pgroupList = existedUser.getpGroupList();
			pgroupList.add(pGroup);
			userDao.updateUser(existedUser);
		} else {
			return;
		}
		// 更新pgroup的userList
		user = userDao.getUserByUsername(user.getUsername());
		user.setpGroupList(null);
		userList.add(user);
		pGroup.setUserList(userList);
		pGroupDao.updatePGroup(pGroup);
	}

	private boolean isUserInUserList(User user, List<User> userList) {
		for (User u : userList) {
			if (user.getId().equals(u.getId())) {
				return true;
			}
		}
		return false;
	}

	public List<PGroup> getPGroupsByParentID(String parentId) {
		return pGroupDao.getPGroupsByParentID(parentId);
	}

	public List<User> getUsersByPGroupID(String pgroupId) {
		PGroup pGroup = pGroupDao.getGroupById(pgroupId);
		return pGroup.getUserList();
	}

	public List<User> getAllUsersByPGroupID(String pgroupId) {
		LinkedList<String> parentPGroupIds = new LinkedList<String>();
		parentPGroupIds.add(pgroupId);
		List<User> userList = new ArrayList<User>();
		boolean canDelete = true;
		while (!parentPGroupIds.isEmpty()) {
			String parentPGroupId = parentPGroupIds.removeFirst();
			PGroup parentPGroup = pGroupDao.getGroupById(parentPGroupId);
			for (User user : parentPGroup.getUserList()) {
				PGroup tempGroup = new PGroup();
				tempGroup.setName(parentPGroup.getName());
				user.setpGroupList(Arrays.asList(tempGroup));
				user.setCanDelete(canDelete);
				userList.add(user);
			}
			canDelete = false;
			List<PGroup> childPGroups = pGroupDao.getPGroupsByParentID(parentPGroupId);
			for (PGroup pGroup : childPGroups) {
				parentPGroupIds.add(pGroup.getId());
			}
		}
		return userList;
	}

	public boolean canDeletePGroup(String pgroupId, String adminName) {
		PGroup pGroup = pGroupDao.getGroupById(pgroupId);
		if (pGroup != null) {
			PGroup parentPGroup = pGroupDao.getGroupById(pGroup.getParentPGroupID());
			if (parentPGroup != null) {
				return StringUtils.equals(parentPGroup.getAdmin().getUsername(), adminName)
						&& pGroup.getUserList().size() == 0 && pGroupDao.getPGroupsByParentID(pgroupId).size() == 0;
			}
		}
		return false;
	}

	public void deletePGroup(String pgroupId, String adminName) {
		if (canDeletePGroup(pgroupId, adminName)) {
			pGroupDao.deletePGroupByID(pgroupId);
		}
	}

	public void deleteUserByID(String userId) {
		// 需要删除代理商下的userList
		User user = userDao.getUserById(userId);
		List<PGroup> pgroupList = user.getpGroupList();
		for (PGroup pgroup : pgroupList) {
			String id = pgroup.getId();
			PGroup pgroupDetails = pGroupDao.getGroupById(id);
			List<User> userList = pgroupDetails.getUserList();
			for (int i = 0; i < userList.size(); i++) {
				if (userList.get(i).getId().equals(userId)) {
					userList.remove(i);
					break;
				}
			}
			pGroupDao.updatePGroup(pgroupDetails);
		}
		// 删除用户
		userDao.deleteUserByID(userId);
	}

	public void updateUserStatusByID(String userId, PeopleStatus status) {
		// 更新User表的状态
		userDao.updateUserStatusByID(userId, status);
		// 更新PGroup中UserList中的状态
		User updateUser = userDao.getUserById(userId);
		List<PGroup> pgroupList = updateUser.getpGroupList();
		for (PGroup pgroup : pgroupList) {
			PGroup updatePGroup = pGroupDao.getGroupById(pgroup.getId());
			List<User> userList = updatePGroup.getUserList();
			for (User user : userList) {
				if (StringUtils.equals(user.getId(), userId)) {
					user.setStatus(status);
				}
			}
			pGroupDao.updatePGroup(updatePGroup);
		}
	}

	public PGroup getGroupByAdminUserName(String adminName) {
		return pGroupDao.getGroupByAdminUserName(adminName);
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
}
