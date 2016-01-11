package fortune.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-28.
 */
@Component
public class BeanHolder {

    @Autowired
    private WagerService wagerService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private OddsService oddsService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private UserService userService;

    @Autowired
    private JobTrackerService jobTrackerService;

    @Autowired
    private TuishuiService tuishuiService;

    private static BeanHolder beanHolder;

    public BeanHolder() {
        beanHolder = this;
    }

    public static WagerService getWagerService() {
        return beanHolder.wagerService;
    }

    public static LotteryService getLotteryService() {
        return beanHolder.lotteryService;
    }

    public static OddsService getOddsService() {
        return beanHolder.oddsService;
    }

    public static ResultService getResultService() {
        return beanHolder.resultService;
    }

    public static UserService getUserService() {
        return beanHolder.userService;
    }

    public static JobTrackerService getJobTrackerService() {
        return beanHolder.jobTrackerService;
    }

    public static TuishuiService getTuishuiService() {
        return beanHolder.tuishuiService;
    }
}
