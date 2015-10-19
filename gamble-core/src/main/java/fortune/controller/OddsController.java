package fortune.controller;

import common.Utils;
import fortune.pojo.Odds;
import fortune.service.OddsService;
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
@RequestMapping("/odds")
public class OddsController {

    @Autowired
    private OddsService oddsService;

    @RequestMapping(value = "save", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void saveOdds4LotteryMarkSix(@RequestBody @Valid Odds odds, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            return;
        }
        oddsService.saveOdds(odds);
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Odds> getOddsAll(HttpServletResponse response) {
        return oddsService.getAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Odds getOddsById(@PathVariable("id") int oddsId) {
        return oddsService.getOddsById(oddsId);
    }
}
