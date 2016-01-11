package fortune.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.Utils;
import fortune.dao.LotteryDao;
import fortune.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utililty.PropertyHolder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.util.*;

/**
 * Created by tangl9 on 2015-10-14.
 */
@Service
public class LotteryService {

    @Autowired
    private LotteryDao lotteryDao;

    @Transactional
    public List<LotteryMarkSix> getLotteryMarkSixAll() {
        Utils.logger.info("get all lottery mark six");
        return lotteryDao.getLotteryMarkSixAll();
    }

    @Transactional
    public LotteryMarkSix getLotteryMarkSix(int lotteryIssue) {
        Utils.logger.info("get lottery mark six of issue {}", lotteryIssue);
        return lotteryDao.getLotteryMarkSix(lotteryIssue);
    }

    @CacheEvict(value = "fortune", allEntries = true)
    @Transactional
    public void saveLotteryMarkSix(LotteryMarkSix lotteryMarkSix) {
        Utils.logger.info("save lottery mark six");
        try {
            HashSet<Integer> numberSet = new HashSet<>();
            numberSet.add(lotteryMarkSix.getOne());
            numberSet.add(lotteryMarkSix.getTwo());
            numberSet.add(lotteryMarkSix.getThree());
            numberSet.add(lotteryMarkSix.getFour());
            numberSet.add(lotteryMarkSix.getFive());
            numberSet.add(lotteryMarkSix.getSix());
            numberSet.add(lotteryMarkSix.getSpecial());
            if (numberSet.size() != 7) {
                throw new RuntimeException("开奖号码有重复");
            }
            for (Integer number : numberSet) {
                if (number > 49) {
                    throw new RuntimeException("开奖号码超过最大号码49");
                }
            }
            NextLotteryMarkSixInfo nextLotteryMarkSixInfo = getNextLotteryMarkSixInfo();
            if (lotteryMarkSix.getIssue() == 0) {
                lotteryMarkSix.setIssue(nextLotteryMarkSixInfo.getIssue());
            }

            lotteryDao.saveLotteryMarkSix(lotteryMarkSix);
        } catch (Exception e) {
            throw new RuntimeException("保存六合彩出错");
        }
    }

    public JSONArray getLotteryMarkSixType() {
        Utils.logger.info("get lottery mark six type");
        JSONArray jsonArray = new JSONArray();
        EnumSet<LotteryMarkSixType> currEnumSet = EnumSet.allOf(LotteryMarkSixType.class);
        for (LotteryMarkSixType type : currEnumSet) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", type.toString());
            jsonObject.put("value", type.getType());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    
    public JSONArray getLotteryMarkSixTopType() {
        Utils.logger.info("get lottery mark six top type");
        JSONArray jsonArray = new JSONArray();
        LotteryMarkSixType.getTopTypeList().stream().forEach(type -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", type.toString());
            jsonObject.put("title", type.getType());
            jsonArray.add(jsonObject);
        });
        return jsonArray;
    }

    @Transactional
    public int getLatestLotteryIssue() {
        Utils.logger.info("get latest lottery issue");
        return lotteryDao.getLatestLotteryIssue();
    }

    @Cacheable(value = "fortune")
    @Transactional
    public List<LotteryMarkSix> getLotteryMarkSixByPagination(int from, int count) {
        Utils.logger.info("get paginated lottery mark six from {} count {}", from, count);
        return lotteryDao.getLotteryMarkSixByPagination(from, count);
    }

    @Transactional
    public NextLotteryMarkSixInfo getNextLotteryMarkSixInfo() {
        Utils.logger.info("get next lottery mark six info");
        NextLotteryMarkSixInfo nextLotteryMarkSixInfo = new NextLotteryMarkSixInfo();
        int latestIssue = getLatestLotteryIssue();
        nextLotteryMarkSixInfo.setIssue(latestIssue + 1);
        LotteryMarkSix lotteryMarkSix = getLotteryMarkSix(latestIssue);
        Instant instant = Instant.ofEpochMilli(lotteryMarkSix.getTimestamp().getTime());
        LocalDateTime thatTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        if (thatTime.getMonthValue() == 12 && thatTime.getDayOfMonth() >= 29) {
            if (thatTime.getDayOfMonth() >= 29) {
                if (thatTime.getDayOfMonth() >= 30 || thatTime.getDayOfWeek().getValue() == 6) {
                    nextLotteryMarkSixInfo.setIssue(Integer.parseInt((thatTime.getYear() + 1) + Utils.formatNumber000(1)));
                }
            }
        }
        String[] gambleWeekDays = PropertyHolder.GAMBLE_WAGE_WEEKDAYS;
        List<Integer> weekDaysIntValue = new ArrayList<>();
        for (String day : gambleWeekDays) {
            weekDaysIntValue.add(Integer.parseInt(day));
        }
        thatTime = thatTime.plusDays(1);
        LocalDateTime nextDrawTime = LocalDateTime.of(thatTime.getYear(), thatTime.getMonth(), thatTime.getDayOfMonth(), PropertyHolder.LOTTERY_DRAW_HOUR, PropertyHolder.LOTTERY_DRAW_MINUTE);

        while (!weekDaysIntValue.contains(nextDrawTime.getDayOfWeek().getValue()) || nextDrawTime.isBefore(LocalDateTime.now())){
            nextDrawTime = nextDrawTime.plusDays(1);
        }
        nextLotteryMarkSixInfo.setDate(Date.from(nextDrawTime.atZone(ZoneId.systemDefault()).toInstant()));
        LocalDateTime nextWageTime = LocalDateTime.of(nextDrawTime.getYear(), nextDrawTime.getMonth(), nextDrawTime.getDayOfMonth(), PropertyHolder.GAMBLE_WAGE_HOUR_START, PropertyHolder.GAMBLE_WAGE_MINUTE_START);
        nextLotteryMarkSixInfo.setWageDate(Date.from(nextWageTime.atZone(ZoneId.systemDefault()).toInstant()));
        return nextLotteryMarkSixInfo;
    }

    @Transactional
    public int getLotteryMarkSixCount() {
        Utils.logger.info("get lottery mark six total count");
        return lotteryDao.getLotteryMarkSixCount();
    }

    @Transactional
    public LotteryMarkSixType getZodiac(int lotteryIssue, int special) {
        LocalDateTime localDateTime;
        if (lotteryIssue == 0) {
            localDateTime = LocalDateTime.now();
        } else {
            LotteryMarkSix lotteryMarkSix = getLotteryMarkSix(lotteryIssue);
            Instant instant = lotteryMarkSix.getTimestamp().toInstant();
            ZoneId zone = ZoneId.systemDefault();
            localDateTime = LocalDateTime.ofInstant(instant, zone);
        }
        Solar solar = new Solar();
        solar.solarYear = localDateTime.getYear();
        solar.solarMonth = localDateTime.getMonthValue();
        solar.solarDay = localDateTime.getDayOfMonth();
        Lunar lunar = LunarSolarConverter.SolarToLunar(solar);
        int currentLunarYear = lunar.lunarYear;
        int baseYear = 2015;
        int number = special % 12;
        List<LotteryMarkSixType> zodiacList = new ArrayList<>();
        zodiacList.add(LotteryMarkSixType.ZODIAC_YANG);
        zodiacList.add(LotteryMarkSixType.ZODIAC_HOU);
        zodiacList.add(LotteryMarkSixType.ZODIAC_JI);
        zodiacList.add(LotteryMarkSixType.ZODIAC_GOU);
        zodiacList.add(LotteryMarkSixType.ZODIAC_ZHU);
        zodiacList.add(LotteryMarkSixType.ZODIAC_SHU);
        zodiacList.add(LotteryMarkSixType.ZODIAC_NIU);
        zodiacList.add(LotteryMarkSixType.ZODIAC_HU);
        zodiacList.add(LotteryMarkSixType.ZODIAC_TU);
        zodiacList.add(LotteryMarkSixType.ZODIAC_LONG);
        zodiacList.add(LotteryMarkSixType.ZODIAC_SHE);
        zodiacList.add(LotteryMarkSixType.ZODIAC_MA);
        int index = (currentLunarYear - baseYear - number + 1) >= 0 ? (currentLunarYear - baseYear - number + 1) : 12 + (currentLunarYear - baseYear - number + 1);
        return zodiacList.get(index);
    }
}