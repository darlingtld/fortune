package fortune.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lingda on 2015/10/24.
 */
@Scope("prototype")
@Controller
@RequestMapping("/common")
public class CommonController {

    @RequestMapping(value = "platform_name", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONObject getPlatformName() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "水晶宫");
        jsonObject.put("corp", "海盗船娱乐会所");
        return jsonObject;
    }

}
