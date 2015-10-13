package service;

import com.google.common.collect.Sets;
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
import fortune.pojo.Role;

import java.util.Date;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PGroupService pGroupService;

    @Test
    public void createUser() {
        User user = new User();
        user.setUsername("lingda tang");
        user.setPassword("12345");
        user.setRole(Role.NORMAL_USER);
        user.setLastLoginTime(new Date());
        userService.createUser(user);
    }

    @Test
    public void createGroup() {
        PGroup PGroup = new PGroup();
        PGroup.setName("hongkong");
        List<User> userList = userService.getAll();
        PGroup.setUsers(Sets.newHashSet(userList));
        pGroupService.createGroup(PGroup);
    }

    @Test
    public void loginUser() {
        User user = userService.login("lingda", "123");
        System.out.println(user+"==>"+user.getPGroups().size());

    }
}
