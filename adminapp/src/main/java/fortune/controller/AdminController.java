package fortune.controller;

import common.Utils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import fortune.pojo.PGroup;
import fortune.service.ThriftService;
import fortune.service.UserService;
import utililty.PropertyHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

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
    boolean deposit(@PathVariable("user_id") String userId,
                    @PathVariable("account") double account) {
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
    PGroup login(@RequestBody JSONObject loginStub, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String username = loginStub.getString("username");
        String password = loginStub.getString("password");
        String kaptchaReceived = loginStub.getString("kaptcha");
        String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (kaptchaReceived == null || !kaptchaReceived.equals(kaptchaExpected)) {
            response.setHeader(Utils.HEADER_MESSAGE, new String("验证码错误".getBytes("utf-8"), "iso-8859-1"));
            response.setStatus(HttpStatus.SC_EXPECTATION_FAILED);
            return null;
        }
        return userService.adminLogin(username, password);
    }

}
