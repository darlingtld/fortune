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
}
