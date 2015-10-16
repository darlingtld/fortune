package fortune.service;

import common.Utils;
import fortune.dao.OddsDao;
import fortune.pojo.Odds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangl9 on 2015-10-16.
 */
@Service
public class OddsService {

    @Autowired
    private OddsDao oddsDao;

    @Transactional
    public Odds getOddsById(int oddsId) {
        Utils.logger.info("get odds by id {}", oddsId);
        return oddsDao.getOddsById(oddsId);
    }

    @Transactional
    public void saveOdds(Odds odds) {
        Utils.logger.info("save odds");
        oddsDao.saveOdds(odds);
    }

    @Transactional
    public List<Odds> getAll() {
        Utils.logger.info("get all lottery odds");
        return oddsDao.getAll();
    }
}
