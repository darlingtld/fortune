package fortune.service;

import common.Utils;
import fortune.dao.PGroupDao;
import fortune.pojo.PGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tangl9 on 2015-10-13.
 */
@Service
public class PGroupService {

    @Autowired
    private PGroupDao PGroupDao;

    @Transactional
    public PGroup getGroupById(int id) {
        Utils.logger.info("get PGroup by id {}", id);
        return PGroupDao.getGroupById(id);
    }

    @Transactional
    public void createGroup(PGroup PGroup) {
        Utils.logger.info("create PGroup {}", PGroup);
        PGroupDao.createGroup(PGroup);
    }
}
