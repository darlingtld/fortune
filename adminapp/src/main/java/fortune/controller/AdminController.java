package fortune.controller;

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
	public @ResponseBody boolean deposit(@PathVariable("user_id") String userId,
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
	public @ResponseBody PGroup login(@RequestBody JSONObject loginStub) {
		String username = loginStub.getString("username");
		String password = loginStub.getString("password");
		return userService.adminLogin(username, password);
	}

}
