package fortune.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.Utils;
import fortune.pojo.PGroup;
import fortune.pojo.PeopleStatus;
import fortune.pojo.User;
import fortune.service.PGroupService;
import fortune.service.UserService;

/**
 * Created by tangl9 on 2015-10-14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/pgroup")
public class PGroupController {

    @Autowired
    private PGroupService pGroupService;

    @Autowired
    private UserService userService;

    /**
     * 新增一个代理商
     *
     * @param pGroup
     * @param result
     * @param response
     */
    @RequestMapping(value = "add_pgroup", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void savePgroup(@RequestBody @Valid PGroup pGroup, BindingResult result,
                    HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            return;
        }
        try {
            pGroupService.createGroup(pGroup);
        } catch (Exception e) {
            response.setHeader(Utils.HEADER_MESSAGE, e.getMessage());
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        }
    }

    /**
     * 代理商添加用户
     *
     * @param pGroupId
     * @param user
     * @param result
     * @param response
     */
    @RequestMapping(value = "{pgroup_id}/add_user", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void addUser(@PathVariable("pgroup_id") String pGroupId, @RequestBody @Valid User user,
                 BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            return;
        }
        try {
            pGroupService.addUser(pGroupId, user);
        } catch (Exception e) {
            response.setHeader(Utils.HEADER_MESSAGE, e.getMessage());
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        }
    }

    @RequestMapping(value = "delete/pgroup/{pgroupId}/{adminName}", method = RequestMethod.POST)
    public
    @ResponseBody
    void deletePGroup(@PathVariable("pgroupId") String pgroupId,
                      @PathVariable("adminName") String adminName) {
        pGroupService.deletePGroup(pgroupId, adminName);
    }

    @RequestMapping(value = "{pGroupId}/delete/user/{userId}", method = RequestMethod.POST)
    public
    @ResponseBody
    void deleteUser(@PathVariable("pGroupId") String pGroupId, @PathVariable("userId") String userId) {
        pGroupService.deleteUserByID(pGroupId, userId);
    }

    @RequestMapping(value = "enable/user/{userId}", method = RequestMethod.POST)
    public
    @ResponseBody
    void enableUser(@PathVariable("userId") String userId) {
        pGroupService.updateUserStatusByID(userId, PeopleStatus.ENABLED);
    }

    @RequestMapping(value = "disable/user/{userId}", method = RequestMethod.POST)
    public
    @ResponseBody
    void disableUser(@PathVariable("userId") String userId) {
        pGroupService.updateUserStatusByID(userId, PeopleStatus.DISABLED);
    }

    @RequestMapping(value = "credit_setting/user/{userId}/{creditValue}", method = RequestMethod.POST)
    public
    @ResponseBody
    void disableUser(@PathVariable("userId") String userId,
                     @PathVariable("creditValue") double creditValue) {
        userService.updateUserCreditByID(userId, creditValue);
    }

    @RequestMapping(value = "can_delete/{pgroupId}/{adminName}", method = RequestMethod.GET)
    public
    @ResponseBody
    boolean canOperatePGroup(@PathVariable("pgroupId") String pgroupId,
                             @PathVariable("adminName") String adminName) {
        return pGroupService.canDeletePGroup(pgroupId, adminName);
    }

    @RequestMapping(value = "pgroups/{parentId}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PGroup> getPGroupsByParentID(@PathVariable("parentId") String parentId) {
        return pGroupService.getPGroupsByParentID(parentId);
    }

    @RequestMapping(value = "admin_pgroup/{adminName}", method = RequestMethod.GET)
    public
    @ResponseBody
    PGroup getGroupByAdminUserName(@PathVariable("adminName") String adminName) {
        return pGroupService.getGroupByAdminUserName(adminName);
    }

    @RequestMapping(value = "users/{pgroupId}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<User> getUsersByPGroup(@PathVariable("pgroupId") String pgroupId) {
        return pGroupService.getUsersByPGroupID(pgroupId);
    }

    @RequestMapping(value = "allusers/{pgroupId}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<User> getAllUsersByPGroup(@PathVariable("pgroupId") String pgroupId) {
        return pGroupService.getAllUsersByPGroupID(pgroupId);
    }
    
    @RequestMapping(value = "/{pgroupId}", method = RequestMethod.GET)
    public
    @ResponseBody
    PGroup getPGroupById(@PathVariable("pgroupId") String pgroupId) {
        return pGroupService.getGroupById(pgroupId);
    }

    @RequestMapping(value = "{pgroup_id}/switch_zoufei_status", method = RequestMethod.POST)
    public
    @ResponseBody
    void switchZoufeiStatus(@PathVariable("pgroup_id") String pGroupId, HttpServletResponse response) {
        try {
            pGroupService.switchZoufeiStatus(pGroupId);
        } catch (Exception e) {
            response.setHeader(Utils.HEADER_MESSAGE, e.getMessage());
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        }
    }
    
}
