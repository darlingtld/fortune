package service;

import fortune.service.StatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by tangl9 on 2015-11-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:mvc-dispatcher-servlet.xml" })
public class StatServiceTest {

    @Autowired
    private StatService statService;

    @Test
    public void generateLotteryMarkSixUserStat(){

    }
}
