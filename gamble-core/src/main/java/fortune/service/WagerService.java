package fortune.service;

import com.google.common.collect.Lists;
import common.Utils;
import fortune.dao.LotteryDao;
import fortune.dao.WagerDao;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryMarkSixWager;
import fortune.pojo.LotteryMarkSixWagerStub;
import fortune.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangl9 on 2015-10-21.
 */
@Service
public class WagerService {

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private UserService userService;

    @Autowired
    private WagerDao wagerDao;

    @Autowired
    private LotteryDao lotteryDao;

    @Transactional
    public LotteryMarkSixWager getLotteryMarkSixWager(String lotteryMarkSixWagerId) {
        Utils.logger.info("get lottery mark six wager id {}", lotteryMarkSixWagerId);
        return wagerDao.getLotteryMarkSixWager(lotteryMarkSixWagerId);
    }

    @Transactional
    public void saveLotteryMarkSixWager(LotteryMarkSixWager lotteryMarkSixWager) {
        Utils.logger.info("save lottery mark six wager {}", lotteryMarkSixWager);

        lotteryMarkSixWager.setLotteryIssue(lotteryService.getNextLotteryMarkSixInfo().getIssue());
        if (lotteryMarkSixWager.getLotteryMarkSixWagerStubList().size() > 0 && lotteryMarkSixWager.getTotalStakes() < 1) {
            double totalStakes = 0;
            for (LotteryMarkSixWagerStub stub : lotteryMarkSixWager.getLotteryMarkSixWagerStubList()) {
                totalStakes += stub.getStakes();
            }
            lotteryMarkSixWager.setTotalStakes(totalStakes);
        }
        // modify used credit account
        User user = userService.getUserById(lotteryMarkSixWager.getUserId());
        if (user.getUsedCreditAccount() + lotteryMarkSixWager.getTotalStakes() > user.getCreditAccount()) {
            throw new ArithmeticException("额度不足！");
        }
        user.setUsedCreditAccount(user.getUsedCreditAccount() + lotteryMarkSixWager.getTotalStakes());
        userService.updateAccount(user);

        wagerDao.saveLotteryMarkSixWager(lotteryMarkSixWager);

    }

    @Transactional
    public void saveLotteryMarkSixWager(List<LotteryMarkSixWager> lotteryMarkSixWagerList) {
        Utils.logger.info("save lottery mark six wager list ");
        double totalStakes = 0;
        for (LotteryMarkSixWager lotteryMarkSixWager : lotteryMarkSixWagerList) {
            lotteryMarkSixWager.setLotteryIssue(lotteryService.getNextLotteryMarkSixInfo().getIssue());
            if (lotteryMarkSixWager.getLotteryMarkSixWagerStubList().size() > 0 && lotteryMarkSixWager.getTotalStakes() < 1) {
                for (LotteryMarkSixWagerStub stub : lotteryMarkSixWager.getLotteryMarkSixWagerStubList()) {
                    totalStakes += stub.getStakes();
                }
            } else {
                totalStakes += lotteryMarkSixWager.getTotalStakes();
            }

        }
        User user = userService.getUserById(lotteryMarkSixWagerList.get(0).getUserId());
        if (user.getUsedCreditAccount() + totalStakes > user.getCreditAccount()) {
            throw new ArithmeticException("额度不足！");
        } else {
            lotteryMarkSixWagerList.forEach(this::saveLotteryMarkSixWager);
        }
    }

    @Transactional
    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(String userId, int lotteryIssue) {
        Utils.logger.info("get lottery mark six wager list of user {}, issue{}", userId, lotteryIssue);
        return wagerDao.getLotteryMarkSixWagerList(userId, lotteryIssue);
    }

    @Transactional
    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(String userId) {
        Utils.logger.info("get all lottery mark six wager list of user {}", userId);
        return wagerDao.getLotteryMarkSixWagerList(userId);
    }

    @Transactional
    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(String userId, String groupId) {
        Utils.logger.info("get lottery mark six wager list of user {}, groupId{}", userId, groupId);
        return wagerDao.getLotteryMarkSixWagerList(userId, groupId);
    }

    @Transactional
    public void deleteLotteryMarkSixWager(String lotteryMarkSixWagerId) {
        Utils.logger.info("delete lottery mark six wager id {}", lotteryMarkSixWagerId);
        wagerDao.deleteLotteryMarkSixWager(lotteryMarkSixWagerId);
    }

    @Transactional
    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(String userId, String pgroupId, int lotteryIssue) {
        Utils.logger.info("get lottery mark six wager list of user {}, pgroupid {}, issue {}", userId, pgroupId,
                lotteryIssue);
        return wagerDao.getLotteryMarkSixWagerList(userId, pgroupId, lotteryIssue);
    }

    @Transactional
    public LotteryMarkSixWager updateLotteryMarkSixWager(LotteryMarkSixWager lotteryMarkSixWager) {
        Utils.logger.info("update lottery mark six wager {}", lotteryMarkSixWager);
        lotteryMarkSixWager.setLotteryIssue(lotteryDao.getLatestLotteryIssue());
        return wagerDao.updateLotteryMarkSixWager(lotteryMarkSixWager);
    }

    @Transactional
    public List<LotteryMarkSixWager> getAll(int lotteryIssue) {
        Utils.logger.info("get all lottery mark six wager of lottery issue {}", lotteryIssue);
        return wagerDao.getAll(lotteryIssue);
    }

    @Transactional
    public List<LotteryMarkSixWager> getLotteryMarkSixWagerListByType(int lotteryIssue,
                                                                      LotteryMarkSixType lotteryMarkSixType) {
        Utils.logger.info("get all lottery mark six wager of lottery issue {}, type {}", lotteryIssue,
                lotteryMarkSixType);
        return wagerDao.getLotteryMarkSixWagerListByType(lotteryIssue, lotteryMarkSixType);
    }

    @Transactional
    public List<LotteryMarkSixWager> getLotteryMarkSixWagerListOfGroup(String groupid, String panlei, int lotteryIssue) {
        Utils.logger.info("get all lottery mark six wager of lottery issue {}, group id {}, panlei", lotteryIssue, groupid, panlei);
        List<LotteryMarkSixWager> wagerList = Lists.newArrayList();
        if (panlei.equalsIgnoreCase("ALL")) {
            for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                wagerList.addAll(wagerDao.getLotteryMarkSixWagerListOfGroup(groupid, pan, lotteryIssue));
            }
        } else {
            wagerList.addAll(wagerDao.getLotteryMarkSixWagerListOfGroup(groupid, panlei, lotteryIssue));
        }
        return wagerList;
    }

    @Transactional
    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(LotteryMarkSixType type, String groupId, String panlei, int issue, int number) {
        Utils.logger.info("get lottery mark six wager list of type {} lottery issue {},group id{}, number {}, panlei {}", type.getType(), issue, groupId, number, panlei);
        return wagerDao.getLotteryMarkSixWagerList(type, groupId, panlei, issue, number);
    }

    /**
     * Get wager list by specific <tt>type</tt>, <tt>groupId</tt>, <tt>panlei</tt>, <tt>issue</tt>, <tt>ballType</tt>. <br>
     * Note: <br> 
     *  - <tt>ballType</tt> is optional. <br>
     *  - <tt>panlei</tt> can be "ALL" <br>
     * 
     */
    @Transactional
    public List<LotteryMarkSixWager> getLotteryMarkSixWagerList(LotteryMarkSixType type, String groupId, String panlei, int issue, LotteryMarkSixType ballType) {
        Utils.logger.info("get lottery mark six wager list of type {} lottery issue {},group id{}, panlei {}", type.getType(), issue, groupId, panlei);
        return wagerDao.getLotteryMarkSixWagerList(type, groupId, panlei, issue, ballType);
    }
    
}
