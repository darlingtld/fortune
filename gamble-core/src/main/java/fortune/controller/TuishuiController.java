package fortune.controller;

import com.alibaba.fastjson.JSONObject;
import common.Utils;
import fortune.pojo.LotteryOdds;
import fortune.pojo.LotteryTuishui;
import fortune.service.CommonService;
import fortune.service.TuishuiService;
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
 * Created by jason on 2015-01-06.
 */
@Scope("prototype")
@Controller
@RequestMapping("/tuishui")
public class TuishuiController {

    @Autowired
    private TuishuiService tuishuiService;

    /**
     * 设置退水
     * 如果设置了type类型，在中奖判断时，会忽略number的情况
     *
     * @param tuishui
     * @param result
     * @param response
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void saveTuishui4LotteryMarkSix(@RequestBody @Valid LotteryTuishui tuishui, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setHeader(Utils.HEADER_MESSAGE, result.getFieldErrors().toString());
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            return;
        }
        tuishuiService.saveTuishui(tuishui);
    }

    /**
     * 根据Id修改退水
     */
    @RequestMapping(value = "change", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    LotteryTuishui changeTuishuiById(@RequestBody JSONObject tuishuiStub, HttpServletResponse response) {
        String tuishuiId = tuishuiStub.getString("tuishuiId");
        double tuishui = tuishuiStub.getDouble("tuishui");
        return tuishuiService.changeTuishui(tuishuiId, tuishui);
    }

    /**
     * 获取所有退水
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryTuishui> getTuishuiAll(HttpServletResponse response) {
        return tuishuiService.getAll();
    }

    /**
     * 根据id获取退水
     *
     * @param tuishuiId
     * @return
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    LotteryTuishui getTuishuiById(@PathVariable("id") String tuishuiId) {
        return tuishuiService.getTuishuiById(tuishuiId);
    }
    
    /**
     * 获取某个代理商对于某一个User的退水
     */
    @RequestMapping(value = "user/{user_id}/group/{group_id}/pan/{panlei}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryTuishui> getTuishui4User(@PathVariable("user_id") String userId, @PathVariable("group_id") String groupId, @PathVariable("panlei") String panlei) {
        return tuishuiService.getTuishui4User(userId, groupId, panlei.toUpperCase());
    }

}
