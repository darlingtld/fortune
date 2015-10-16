package fortune.controller;

import fortune.pojo.Odds;
import fortune.service.OddsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Scope("prototype")
@Controller
@RequestMapping("/gamble")
public class GambleController {

    @Autowired
    private OddsService oddsService;

    @RequestMapping(value = "bet", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void bet() {

    }

    @RequestMapping(value = "odds/{odds_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Odds getOddsById(@PathVariable("odds_id") int oddsId) {
        return oddsService.getOddsById(oddsId);
    }
}