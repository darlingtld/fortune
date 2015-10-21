package fortune.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.Utils;
import fortune.pojo.LotteryBall;
import fortune.pojo.LotteryMarkSix;
import fortune.service.LotteryService;
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
 * Created by tangl9 on 2015-10-14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/lottery")
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryMarkSix> getLotteryMarkSixAll() {
        return lotteryService.getLotteryMarkSixAll();
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void saveLotteryMarkSix(@RequestBody @Valid LotteryMarkSix lotteryMarkSix, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            return;
        }
        lotteryService.saveLotteryMarkSix(lotteryMarkSix);
    }

    @RequestMapping(value = "type", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONArray getLotteryMarkSixType() {
        return lotteryService.getLotteryMarkSixType();
    }

    @RequestMapping(value = "ball_number/{number}", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONObject getBall(@PathVariable("number") int number) {
        LotteryBall lotteryBall = LotteryBall.valueOf("NUM_" + number);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("number", lotteryBall.getNumber());
        jsonObject.put("color", lotteryBall.getColor());
        return jsonObject;
    }

}
