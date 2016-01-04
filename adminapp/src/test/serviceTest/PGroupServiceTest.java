package serviceTest;

import fortune.pojo.PGroup;
import fortune.pojo.User;
import fortune.service.PGroupService;
import fortune.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by tangl9 on 2015-11-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class PGroupServiceTest {

    @Autowired
    private PGroupService pGroupService;

    @Autowired
    private UserService userService;

    @Test
    public void changeAdmin(){
        User user = userService.getUserByUsername("user4");
        PGroup pGroup = pGroupService.getPGroupByName("台湾");
        pGroupService.changeAdmin(pGroup, user);
    }
}
