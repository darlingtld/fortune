package fortune.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import fortune.pojo.PlatformPeriod;
import fortune.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import utililty.PropertyHolder;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created by lingda on 2015/10/24.
 */
@Scope("prototype")
@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "platform_name", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONObject getPlatformName() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "六合彩");
        jsonObject.put("corp", "淞沪路面对面博彩有限公司");
        return jsonObject;
    }

    @RequestMapping(value = "platform_period", method = RequestMethod.GET)
    public
    @ResponseBody
    PlatformPeriod getPlatformPeriod() {
        return commonService.getPlatformPeriod();
    }

}
