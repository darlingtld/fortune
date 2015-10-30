package fortune.controller;

import fortune.pojo.User;
import fortune.service.ThriftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tangl9 on 2015-10-14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ThriftService thriftService;

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

}
