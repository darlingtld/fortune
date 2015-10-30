package fortune.service;

import common.Utils;
import fortune.dao.PGroupDao;
import fortune.dao.UserDao;
import fortune.pojo.PGroup;
import fortune.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public PGroup getGroupById(String id) {
        Utils.logger.info("get pGroup by id {}", id);
        return pGroupDao.getGroupById(id);
    }

    @Transactional
    public void createGroup(PGroup pGroup) {
        Utils.logger.info("create pGroup {}", pGroup);
        for (User user : pGroup.getUserList()) {
            user.setpGroupList(null);
        }
        User admin = pGroup.getAdmin();
        admin.setpGroupList(null);
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
        if (!isUserInUserList(user, pGroup.getUserList())) {
            List<PGroup> pGroupListTmp = user.getpGroupList();
            user.setpGroupList(null);
            pGroup.getAdmin().setpGroupList(null);
            pGroup.getUserList().add(user);
            pGroup = pGroupDao.updatePGroup(pGroup);
            pGroup.setUserList(null);
            pGroup.setSubPGroupList(null);
            user.setpGroupList(pGroupListTmp);
            user.getpGroupList().add(pGroup);
            userDao.updateUser(user);
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
}
