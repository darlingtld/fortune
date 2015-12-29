package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fortune.pojo.*;
import fortune.service.LotteryService;
import fortune.service.OddsService;
import fortune.service.PGroupService;
import fortune.service.UserService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

/**
 * Created by tangl9 on 2015-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class LotteryServiceTest {

    @Autowired
    private LotteryService lotteryService;

    @Test
    public void saveLottery() {
        DateTime dateTime = new DateTime();
        for (int i = 300; i <= 300; i++) {
            LotteryMarkSix tmp = lotteryService.getLotteryMarkSix(i);
            if (tmp != null) {
                System.out.println(i + " bingo");
                continue;
            }
            LotteryMarkSix lotteryMarkSix = new LotteryMarkSix();
            Set<Integer> integerSet = new HashSet<Integer>();
            int num = new Random().nextInt(49) + 1;
            lotteryMarkSix.setOne(num);
            integerSet.add(num);
            while (integerSet.contains(num)) {
                num = new Random().nextInt(49) + 1;
            }
            lotteryMarkSix.setTwo(num);
            integerSet.add(num);
            while (integerSet.contains(num)) {
                num = new Random().nextInt(49) + 1;
            }
            lotteryMarkSix.setThree(num);
            integerSet.add(num);
            while (integerSet.contains(num)) {
                num = new Random().nextInt(49) + 1;
            }
            lotteryMarkSix.setFour(num);
            integerSet.add(num);
            while (integerSet.contains(num)) {
                num = new Random().nextInt(49) + 1;
            }
            lotteryMarkSix.setFive(num);
            integerSet.add(num);
            while (integerSet.contains(num)) {
                num = new Random().nextInt(49) + 1;
            }
            lotteryMarkSix.setSix(num);
            integerSet.add(num);
            while (integerSet.contains(num)) {
                num = new Random().nextInt(49) + 1;
            }
            lotteryMarkSix.setSpecial(num);
            integerSet.add(num);
            while (integerSet.contains(num)) {
                num = new Random().nextInt(49) + 1;
            }

            lotteryMarkSix.setIssue(i);


            lotteryMarkSix.setTimestamp(dateTime.toDate());
            dateTime = dateTime.plusDays(1);

            System.out.println(lotteryMarkSix);
            lotteryService.saveLotteryMarkSix(lotteryMarkSix);
        }
    }

    @Test
    public void getLotteryMarkSixType() {
        JSONArray jsonArray = new JSONArray();
        EnumSet<LotteryMarkSixType> currEnumSet = EnumSet.allOf(LotteryMarkSixType.class);
        for (LotteryMarkSixType type : currEnumSet) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", type.toString());
            jsonObject.put("value", type.getType());
            jsonArray.add(jsonObject);
        }
        System.out.println(jsonArray);
    }

    @Test
    public void getLotteryMarkSixByPagination() {
        System.out.println("---------------");
        lotteryService.getLotteryMarkSixByPagination(10, 20);
        System.out.println("---------------");
        lotteryService.getLotteryMarkSixByPagination(10, 20);
        System.out.println("---------------");
    }

    @Test
    public void getZodiac() {
        int currentYear = 2017;
        int baseYear = 2015;
        int number = 44 % 12;
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
        int index = (currentYear - baseYear - number + 1) >= 0 ? (currentYear - baseYear - number + 1) : 12 + (currentYear - baseYear - number + 1);
        System.out.println(zodiacList.get(index));

    }
}
