package serviceTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fortune.pojo.RealTimeWager;
import fortune.service.StakeDetailService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class StakeDetailTest {

    @Autowired
    StakeDetailService stakeDetailService;
    
    @Test
    public void testGetStakeDetail() {
        String groupId = "563b49fe63130497d5fc6b2c";
        String userId = null;
        String type = "SPECIAL";
        String fromDate = "2015-12-01";
        String toDate = "2016-01-31";
        
        List<RealTimeWager> stakeDetailList = stakeDetailService.getStakeDetailByTimeRange(groupId, userId, type, fromDate, toDate);
        System.out.println(stakeDetailList.size());
    }
}
