package service;

import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixStat;
import fortune.service.StatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by tangl9 on 2015-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class StatServiceTest {

    @Autowired
    private StatService statService;

    @Test
    public void getLotteryMarkSixStatList() {
        String pgroupId = "563338f6e708fad8259ea83f";
        int from = 0;
        int count = 10;
        List<LotteryMarkSixStat> lotteryMarkSixStatList = statService.getLotteryMarkSixStat(pgroupId, from, count);
        lotteryMarkSixStatList.forEach(System.out::println);
    }
}
