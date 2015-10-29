package fortune.controller;

import fortune.pojo.User;
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

    @RequestMapping(value = "detail/{user_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    User getUserById(@PathVariable("user_id") int userId) {
//        use thrift to make the rpc call
        return null;
    }


}
