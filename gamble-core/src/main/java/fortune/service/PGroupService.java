package fortune.service;

import common.Utils;
import fortune.dao.PGroupDao;
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

    @Transactional
    public PGroup getGroupById(String id) {
        Utils.logger.info("get PGroup by id {}", id);
        return pGroupDao.getGroupById(id);
    }

    @Transactional
    public void createGroup(PGroup PGroup) {
        Utils.logger.info("create PGroup {}", PGroup);
        pGroupDao.createGroup(PGroup);
    }

    @Transactional
    public List<PGroup> getGroupAll() {
        Utils.logger.info("get PGroup all");
        return pGroupDao.getGroupAll();
    }

    @Transactional
    public void addUser(String pGroupId, User user) {
        Utils.logger.info("pgroup {} add user {}", pGroupId, user);
        PGroup pGroup = pGroupDao.getGroupById(pGroupId);
        pGroup.getUserList().add(user);
        pGroupDao.updatePGroup(pGroup);
    }
}
