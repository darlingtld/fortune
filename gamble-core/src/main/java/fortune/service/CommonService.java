package fortune.service;

import com.alibaba.fastjson.JSONObject;
import fortune.pojo.PlatformPeriod;
import org.springframework.stereotype.Service;
import utililty.PropertyHolder;

import java.time.LocalDateTime;

/**
 * Created by tangl9 on 2016-01-06.
 */
@Service
public class CommonService {

    public PlatformPeriod getPlatformPeriod() {
        PlatformPeriod platformPeriod = new PlatformPeriod();
        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.getDayOfWeek().getValue() % 2 == 0) {
            if ((localDateTime.getHour() > PropertyHolder.GAMBLE_WAGE_HOUR_START && localDateTime.getHour() < PropertyHolder.GAMBLE_WAGE_HOUR_END)
                    || (localDateTime.getHour() == PropertyHolder.GAMBLE_WAGE_HOUR_START && localDateTime.getMinute() >= PropertyHolder.GAMBLE_WAGE_MINUTE_START)
                    || (localDateTime.getHour() == PropertyHolder.GAMBLE_WAGE_HOUR_END && localDateTime.getMinute() <= PropertyHolder.GAMBLE_WAGE_MINUTE_END)) {
                platformPeriod.setWagePeriod(true);
                platformPeriod.setSettingOddsPeriod(false);
            }
        } else {
            platformPeriod.setWagePeriod(false);
            platformPeriod.setSettingOddsPeriod(true);
        }
        return platformPeriod;
    }
}
