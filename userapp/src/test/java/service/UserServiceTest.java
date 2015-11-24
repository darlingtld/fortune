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
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setPassword("123456");
            user.setRoleList(Arrays.asList(Role.GROUP_ADMIN, Role.NORMAL_USER));
            user.setCreditAccount(120000);
            user.setUsedCreditAccount(2000);
            user.setLastLoginTime(new Date());
            userService.createUser(user);
        }
    }

    @Test
    public void createGroup() {
        List<String> groupNames = Arrays.asList("上海", "北京", "香港", "台湾");
        for (String groupName : groupNames) {
            PGroup PGroup = new PGroup();
            PGroup.setName(groupName);
            PGroup.setAdmin(userService.getUserByUsername("user1"));
            pGroupService.createGroup(PGroup);
        }
    }

    @Test
    public void loginUser() {
        User user = userService.login("lingda", "123");
        System.out.println(user + "==>" + user.getpGroupList().size());

    }

    @Test
    public void loginAdmin() {
        PGroup pGroup = userService.adminLogin("darling", "123");
        System.out.println(pGroup);

    }

    @Test
    public void pGroupAddUser() {
        User user = userService.getUserByUsername("user2");
        User user1 = userService.getUserByUsername("user3");
        List<PGroup> pGroupList = pGroupService.getGroupAll();
        for (PGroup pGroup : pGroupList) {
            pGroupService.addUser(pGroup.getId(), user);
            pGroupService.addUser(pGroup.getId(), user1);
        }
    }
}
