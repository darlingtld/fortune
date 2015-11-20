package fortune.controller;

import com.alibaba.fastjson.JSONObject;
import common.Utils;
import fortune.pojo.PGroup;
import fortune.pojo.User;
import fortune.service.PGroupService;
import fortune.service.ThriftService;
import fortune.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by tangl9 on 2015-10-14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/administrator")
public class AdminController {

    @Autowired
    private ThriftService thriftService;

    @Autowired
    private UserService userService;

    @Autowired
    private PGroupService pGroupService;

    /**
     * 管理员或者代理商给用户充值
     *
     * @param userId
     * @param account
     * @return
     */
    @RequestMapping(value = "deposit/{user_id}/account/{account}", method = RequestMethod.POST)
    public
    @ResponseBody
    boolean deposit(@PathVariable("user_id") String userId, @PathVariable("account") double account) {
        return thriftService.deposit(userId, account);
    }

    /**
     * 管理员的登录
     *
     * @param loginStub
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    PGroup login(@RequestBody JSONObject loginStub) {
        String username = loginStub.getString("username");
        String password = loginStub.getString("password");
        return userService.adminLogin2(username, password);
    }

    /**
     * 修改代理商的管理员
     *
     * @param pgroup
     * @return
     */
    @RequestMapping(value = "changeAdmin", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    PGroup changeAdmin(@RequestBody PGroup pgroup, HttpServletResponse response) {
        try {
            return pGroupService.changeAdmin(pgroup, pgroup.getAdmin());
        } catch (Exception e) {
            response.setHeader(Utils.HEADER_MESSAGE, e.getMessage());
            response.setStatus(HttpStatus.CONFLICT.value());
            return null;
        }
    }

}
