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
import fortune.pojo.User;
import fortune.service.PGroupService;

/**
 * Created by tangl9 on 2015-10-14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/pgroup")
public class PGroupController {

	@Autowired
	private PGroupService pGroupService;

	/**
	 * 新增一个代理商
	 * 
	 * @param pGroup
	 * @param result
	 * @param response
	 */
	@RequestMapping(value = "add_pgroup", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody void savePgroup(@RequestBody @Valid PGroup pGroup, BindingResult result,
			HttpServletResponse response) {
		if (result.hasErrors()) {
			response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
			response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			return;
		}

		pGroupService.createGroup(pGroup);
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
	public @ResponseBody void addUser(@PathVariable("pgroup_id") String pGroupId, @RequestBody @Valid User user,
			BindingResult result, HttpServletResponse response) {
		if (result.hasErrors()) {
			response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
			response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
			return;
		}

		pGroupService.addUser(pGroupId, user);
	}

	@RequestMapping(value = "pgroups/{parentId}", method = RequestMethod.GET)
	public @ResponseBody List<PGroup> getPGroups(@PathVariable("parentId") String parentId) {
		return pGroupService.getPGroupsByParentID(parentId);
	}

	@RequestMapping(value = "users/{pgroupId}", method = RequestMethod.GET)
	public @ResponseBody List<User> getUsersByPGroup(@PathVariable("pgroupId") String pgroupId) {
		return pGroupService.getUsersByPGroupID(pgroupId);
	}
}
