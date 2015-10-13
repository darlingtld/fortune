package fortune.controller;

import com.alibaba.fastjson.JSONObject;
import fortune.pojo.User;
import fortune.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    User getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    /*
    {name:xxx, password:xxx}
     */
    @RequestMapping(value = "login", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    User login(@RequestBody JSONObject loginStub) {
        return userService.login(loginStub.getString("name"), loginStub.getString("password"));
    }
}