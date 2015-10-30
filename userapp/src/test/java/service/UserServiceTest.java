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
        user.setUsername("lingda tang");
        user.setPassword("12345");
        user.setRoleList(Arrays.asList(Role.NORMAL_USER));
        user.setLastLoginTime(new Date());
        userService.createUser(user);
    }

    @Test
    public void createGroup() {
        PGroup PGroup = new PGroup();
        PGroup.setName("hongkong");
        List<User> userList = userService.getAll();
        PGroup.setUserList(userList);
        pGroupService.createGroup(PGroup);
    }

    @Test
    public void loginUser() {
        User user = userService.login("lingda", "123");
        System.out.println(user + "==>" + user.getpGroupList().size());

    }

    @Test
    public void pGroupAddUser() {
        User user = userService.getUserById("5632fb46e708fd4777345b7a");
        pGroupService.addUser("563326b4e708dced68510126", user);
    }
}
