package fortune.controller;

import com.alibaba.fastjson.JSONObject;
import common.Utils;
import fortune.pojo.User;
import fortune.service.ActionTraceService;
import fortune.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import password.PasswordEncryptUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Scope("prototype")
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActionTraceService actionTraceService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    User getUserById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    /*
    {name:xxx, password:xxx}
     */
    @RequestMapping(value = "login", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    User login(@RequestBody JSONObject loginStub, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String kaptchaReceived = loginStub.getString("kaptcha");
        String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (kaptchaReceived == null || !kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
            response.setHeader(Utils.HEADER_MESSAGE, new String("验证码错误".getBytes("utf-8"), "iso-8859-1"));
            response.setStatus(org.apache.http.HttpStatus.SC_EXPECTATION_FAILED);
            return null;
        }
        User user = userService.login(loginStub.getString("name"), loginStub.getString("password"));
        if (user == null) {
            response.setHeader(Utils.HEADER_MESSAGE, new String("用户名或密码错误".getBytes("utf-8"), "iso-8859-1"));
            response.setStatus(org.apache.http.HttpStatus.SC_FORBIDDEN);
            return null;
        }
        actionTraceService.save(loginStub.getString("name"), "user login", request);

        return user;
    }

    @RequestMapping(value = "change_pass", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    User changePassword(@RequestBody JSONObject jsonObject, HttpServletResponse response) throws UnsupportedEncodingException {
        User user = userService.getUserById(jsonObject.getString("userid"));
        String originalPass = jsonObject.getString("originalPass");
        if (!PasswordEncryptUtil.matches(originalPass, user.getPassword())) {
            response.setHeader(Utils.HEADER_MESSAGE, new String("原始密码不符".getBytes("utf-8"), "iso-8859-1"));
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        }
        String newPass = jsonObject.getString("newPass");
        String newPass2 = jsonObject.getString("newPass2");
        if (!newPass.equals(newPass2)) {
            response.setHeader(Utils.HEADER_MESSAGE, new String("两次密码不匹配".getBytes("utf-8"), "iso-8859-1"));
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            return user;
        } else {
            return userService.changePass(user, newPass);
        }
    }

}