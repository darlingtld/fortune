package fortune.controller;

import com.alibaba.fastjson.JSONObject;
import fortune.pojo.User;
import fortune.service.UserService;
import fortune.utililty.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Scope("prototype")
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

    /*
    {name:xxx, password:xxx}
     */
    @RequestMapping(value = "register", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void register(@RequestBody JSONObject registerStub, HttpServletResponse response) {
        try {
            userService.register(registerStub.getString("name"), registerStub.getString("password"));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.CONFLICT.value());
            response.setHeader(Utils.HEADER_MESSAGE, e.getMessage());
        }
    }
}