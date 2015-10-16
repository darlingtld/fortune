package fortune.controller;

import fortune.pojo.LotteryMarkSix;
import fortune.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by tangl9 on 2015-10-14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/fortune")
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryMarkSix> getUserById() {
        return lotteryService.getLotteryMarkSixAll();
    }
}
