package fortune.controller;

import fortune.pojo.PGroup;
import fortune.service.PGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    PGroup getPGroupById(@PathVariable("id") String id) {
        return pGroupService.getGroupById(id);
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PGroup> getPGroupAll() {
        return pGroupService.getGroupAll();
    }


}
