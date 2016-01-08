package fortune.service;

import fortune.pojo.PlatformPeriod;
import org.springframework.stereotype.Service;
import utililty.PropertyHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangl9 on 2016-01-06.
 */
@Service
public class CommonService {

    public PlatformPeriod getPlatformPeriod() {
        PlatformPeriod platformPeriod = new PlatformPeriod();
        LocalDateTime localDateTime = LocalDateTime.now();
        String[] gambleWeekDays = PropertyHolder.GAMBLE_WAGE_WEEKDAYS;
        List<Integer> weekDaysIntValue = new ArrayList<>();
        for (String day : gambleWeekDays) {
            weekDaysIntValue.add(Integer.parseInt(day));
        }
        if (weekDaysIntValue.contains(localDateTime.getDayOfWeek().getValue())) {
            if ((localDateTime.getHour() > PropertyHolder.GAMBLE_WAGE_HOUR_START && localDateTime.getHour() < PropertyHolder.GAMBLE_WAGE_HOUR_END)
                    || (localDateTime.getHour() == PropertyHolder.GAMBLE_WAGE_HOUR_START && localDateTime.getMinute() >= PropertyHolder.GAMBLE_WAGE_MINUTE_START)
                    || (localDateTime.getHour() == PropertyHolder.GAMBLE_WAGE_HOUR_END && localDateTime.getMinute() <= PropertyHolder.GAMBLE_WAGE_MINUTE_END)) {
                platformPeriod.setWagePeriod(true);
                platformPeriod.setSetOddsPeriod(false);
            } else {
                platformPeriod.setWagePeriod(false);
                platformPeriod.setSetOddsPeriod(true);
            }
        } else {
            platformPeriod.setWagePeriod(false);
            platformPeriod.setSetOddsPeriod(true);
        }
//        for test purpose
        platformPeriod.setWagePeriod(true);
        platformPeriod.setSetOddsPeriod(true);
        return platformPeriod;
    }
}
