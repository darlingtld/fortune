package fortune.controller;

import common.Utils;
import fortune.pojo.PGroup;
import fortune.pojo.User;
import fortune.service.PGroupService;
import fortune.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-19.
 */
@Scope("prototype")
@Controller
@RequestMapping("/pgroup")
public class PGroupController {

    @Autowired
    private PGroupService pGroupService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    PGroup getPGroupById(@PathVariable("id") int id) {
        return pGroupService.getGroupById(id);
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PGroup> getPGroupAll() {
        return pGroupService.getGroupAll();
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void savePgroup(@RequestBody @Valid PGroup pGroup, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            return;
        }

        pGroupService.createGroup(pGroup);
    }

    @RequestMapping(value = "{pgroup_id}/add_user", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void addUser(@PathVariable("pgroup_id") int pGroupId, @RequestBody @Valid User user, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            return;
        }

        pGroupService.addUser(pGroupId, user);
    }
}
