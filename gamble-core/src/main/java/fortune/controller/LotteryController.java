package fortune.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.Utils;
import fortune.pojo.LotteryBall;
import fortune.pojo.LotteryMarkSix;
import fortune.pojo.NextLotteryMarkSixInfo;
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

    /**
     * 获取所有的开奖结果
     *
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LotteryMarkSix> getLotteryMarkSixAll() {
        return lotteryService.getLotteryMarkSixAll();
    }

    /**
     * 获取分页的开奖结果
     *
     * @param from
     * @param count
     * @return
     */
    @RequestMapping(value = "pagination/from/{from}/count/{count}", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONObject getLotteryMarkSixPagination(@PathVariable("from") int from, @PathVariable("count") int count) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lotteryMarkSixList", lotteryService.getLotteryMarkSixByPagination(from, count));
        jsonObject.put("totalItems", lotteryService.getLotteryMarkSixCount());
        return jsonObject;
    }

    /**
     * 保存开奖结果
     *
     * @param lotteryMarkSix
     * @param result
     * @param response
     */
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

    /**
     * 获取所有的博彩类型
     *
     * @return
     */
    @RequestMapping(value = "type", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONArray getLotteryMarkSixType() {
        return lotteryService.getLotteryMarkSixType();
    }

    /**
     * 根据球的数字获取球的颜色
     *
     * @param number
     * @return
     */
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

    /**
     * 获取某一期开奖结果
     *
     * @param lotteryIssue
     * @return
     */
    @RequestMapping(value = "lottery_issue/{lottery_issue}", method = RequestMethod.GET)
    public
    @ResponseBody
    LotteryMarkSix getLotteryMarkSix(@PathVariable("lottery_issue") int lotteryIssue) {
        return lotteryService.getLotteryMarkSix(lotteryIssue);
    }

    /**
     * 获取最新一期的开奖期数
     *
     * @param
     * @return
     */
    @RequestMapping(value = "latest_lottery_issue", method = RequestMethod.GET)
    public
    @ResponseBody
    int getLatestLotteryIssue() {
        return lotteryService.getLatestLotteryIssue();
    }

    /**
     * 获取下一期的开奖六合彩信息
     *
     * @param
     * @return
     */
    @RequestMapping(value = "next_lottery_mark_six_info", method = RequestMethod.GET)
    public
    @ResponseBody
    NextLotteryMarkSixInfo getNextLotteryMarkSixInfo() {
        return lotteryService.getNextLotteryMarkSixInfo();
    }

}
