package fortune.service;

import com.alibaba.druid.util.StringUtils;
import common.Utils;
import fortune.dao.PGroupDao;
import fortune.dao.UserDao;
import fortune.dao.WagerDao;
import fortune.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import password.PasswordEncryptUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-13.
 */
@Service
public class PGroupService {

    @Autowired
    private PGroupDao pGroupDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private TuishuiService tuishuiService;

    @Autowired
    private WagerDao wagerDao;

    @Transactional
    public PGroup getGroupById(String id) {
        Utils.logger.info("get pGroup by id {}", id);
        return pGroupDao.getGroupById(id);
    }

    @Transactional
    public void createGroup(PGroup pGroup) {
        Utils.logger.info("create pGroup {}", pGroup);
        if (pGroupDao.getPGroupByName(pGroup.getName()) != null) {
            throw new RuntimeException("代理商或用户已存在");
        }
//        检查最大金额以及占成比的输入是否合法
        if (pGroup.getMaxStakes() < 0 || pGroup.getMaxStakes() > 10000 * 10000) {
            throw new RuntimeException("最大金额不合法");
        }
        if (pGroup.getZoufeiBy() < 0 || pGroup.getZoufeiBy() > 100) {
            throw new RuntimeException("占成不合法");
        }
        // 插入管理员
        User admin = pGroup.getAdmin();
        admin.setPassword(PasswordEncryptUtil.encrypt(admin.getPassword()));
        // 一个管理员只能管理一个pgroup
        PGroup adminGroup = pGroupDao.getGroupByAdminUserName(admin.getUsername());
        User existedUser = userDao.getUserByUsername(admin.getUsername());
        if (adminGroup == null && existedUser == null) {
            userDao.createUser(admin);
            // 插入pgroup
            pGroup.setAdmin(userDao.getUserByUsername(admin.getUsername()));
            pGroupDao.createGroup(pGroup);
            PGroup fatherPGroup = pGroupDao.getGroupById(pGroup.getParentPGroupID());
//            更新父管理员走飞列表
            if (fatherPGroup != null) {
                List<Zoufei> zoufeiList = fatherPGroup.getZoufeiList();
                Zoufei zoufei = new Zoufei();
                zoufei.setPercentage(pGroup.getZoufeiBy());
                zoufei.setChildPGroupId(pGroupDao.getPGroupByName(pGroup.getName()).getId());
                zoufeiList.add(zoufei);
                pGroupDao.updatePGroup(fatherPGroup);
            }
        } else {
            throw new RuntimeException("代理商或用户已存在");
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
            user.setCreatedByPgroupId(pGroupId);
            userDao.createUser(user);
        }
//        用户是否为一个管理员
        else if (existedUser.getRoleList().contains(Role.GROUP_ADMIN)) {
            throw new RuntimeException("用户为管理员，无法添加");
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

        // 为用户生成默认退水
        tuishuiService.populateTuishui(user.getId(), pGroupId);
    }

    private boolean isUserInUserList(User user, List<User> userList) {
        for (User u : userList) {
            if (user.getUsername().equals(u.getUsername())) {
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
                User creditUser = userDao.getUserById(user.getId());
                if (creditUser != null) {
                    user.setUsedCreditAccount(creditUser.getUsedCreditAccount()); // 信用额度以user表中的为准 
                    user.setCreditAccount(creditUser.getCreditAccount());
                }
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
            String pgroupAdminId = getGroupById(pgroupId).getAdmin().getId();
            pGroupDao.deletePGroupByID(pgroupId);
            userDao.deleteUserByID(pgroupAdminId);
        }
    }

    public void deleteUserByID(String pGroupId, String userId) {
        // 需要删除代理商下的userList
        User user = userDao.getUserById(userId);
        List<PGroup> pgroupList = user.getpGroupList();
        for (PGroup pgroup : pgroupList) {
            if (pgroup.getId().equals(pGroupId)) {
                pgroupList.remove(pgroup);
                break;
            }
        }
        user.setpGroupList(pgroupList);
        PGroup thisPGroup = pGroupDao.getGroupById(pGroupId);
        List<User> userList = thisPGroup.getUserList();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId().equals(userId)) {
                userList.remove(i);
                break;
            }
        }
        pGroupDao.updatePGroup(thisPGroup);
//        删除用户端的代理商
        userDao.updateUser(user);
//        删除该用户在该代理商的下注记录
        wagerDao.deleteLotteryMarkSixWager(pGroupId, userId);

        // 删除退水
        tuishuiService.removeTuishui(userId, pGroupId);

        if (user.getpGroupList().isEmpty()) {
            // 删除用户
            userDao.deleteUserByID(userId);
        }
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
