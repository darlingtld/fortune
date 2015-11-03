package service;

import fortune.pojo.PGroup;
import fortune.pojo.Role;
import fortune.pojo.User;
import fortune.service.PGroupService;
import fortune.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
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
        user.setUsername("darling");
        user.setPassword("123");
        user.setRoleList(Arrays.asList(Role.GROUP_ADMIN));
        user.setLastLoginTime(new Date());
        userService.createUser(user);
    }

    @Test
    public void createGroup() {
        PGroup PGroup = new PGroup();
        PGroup.setName("taipei");
        PGroup.setAdmin(userService.getAll().get(0));
        pGroupService.createGroup(PGroup);
    }

    @Test
    public void loginUser() {
        User user = userService.login("lingda", "123");
        System.out.println(user + "==>" + user.getpGroupList().size());

    }

    @Test
    public void loginAdmin() {
        User user = userService.adminLogin("darling", "123");
        System.out.println(user);

    }

    @Test
    public void pGroupAddUser() {
        User user = userService.getAll().get(0);
        pGroupService.addUser("563338f6e708fad8259ea83f", user);
    }
}
