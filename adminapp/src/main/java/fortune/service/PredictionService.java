package fortune.service;

import fortune.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tangl9 on 2016-01-07.
 */
@Service
public class PredictionService {

    @Autowired
    private LotteryService lotteryService;

    @Transactional
    public List<HashMap<Integer, Double>> predictNextLotteryMarkSix() {
        List<HashMap<Integer, Double>> predictList = new ArrayList<>();
        List<LotteryMarkSix> lotteryMarkSixList = lotteryService.getLotteryMarkSixByPagination(0, 50);
        double totalScore = 10000.0;
        int nextId = lotteryMarkSixList.get(0).getId();
        HashMap<Integer, Double> oneMap = new HashMap<>();
        HashMap<Integer, Double> twoMap = new HashMap<>();
        HashMap<Integer, Double> threeMap = new HashMap<>();
        HashMap<Integer, Double> fourMap = new HashMap<>();
        HashMap<Integer, Double> fiveMap = new HashMap<>();
        HashMap<Integer, Double> sixMap = new HashMap<>();
        HashMap<Integer, Double> specialMap = new HashMap<>();
        for (int i = 1; i <= 49; i++) {
            oneMap.put(i, totalScore);
            twoMap.put(i, totalScore);
            threeMap.put(i, totalScore);
            fourMap.put(i, totalScore);
            fiveMap.put(i, totalScore);
            sixMap.put(i, totalScore);
            specialMap.put(i, totalScore);
        }
        for (LotteryMarkSix lotteryMarkSix : lotteryMarkSixList) {
            double oneScore = oneMap.get(lotteryMarkSix.getOne());
            oneScore -= lotteryMarkSix.getId();
            oneMap.put(lotteryMarkSix.getOne(), oneScore);
            double twoScore = twoMap.get(lotteryMarkSix.getTwo());
            twoScore -= lotteryMarkSix.getId();
            twoMap.put(lotteryMarkSix.getTwo(), twoScore);
            double threeScore = threeMap.get(lotteryMarkSix.getThree());
            threeScore -= lotteryMarkSix.getId();
            threeMap.put(lotteryMarkSix.getThree(), threeScore);
            double fourScore = fourMap.get(lotteryMarkSix.getFour());
            fourScore -= lotteryMarkSix.getId();
            fourMap.put(lotteryMarkSix.getFour(), fourScore);
            double fiveScore = fiveMap.get(lotteryMarkSix.getFive());
            fiveScore -= lotteryMarkSix.getId();
            fiveMap.put(lotteryMarkSix.getFive(), fiveScore);
            double sixScore = sixMap.get(lotteryMarkSix.getSix());
            sixScore -= lotteryMarkSix.getId();
            sixMap.put(lotteryMarkSix.getSix(), sixScore);
            double specialScore = specialMap.get(lotteryMarkSix.getSpecial());
            specialScore -= lotteryMarkSix.getId();
            specialMap.put(lotteryMarkSix.getSpecial(), specialScore);
        }
        for (Integer key : specialMap.keySet()) {
            oneMap.put(key, oneMap.get(key) / (totalScore - nextId / 2));
            twoMap.put(key, twoMap.get(key) / (totalScore - nextId / 2));
            threeMap.put(key, threeMap.get(key) / (totalScore - nextId / 2));
            fourMap.put(key, fourMap.get(key) / (totalScore - nextId / 2));
            fiveMap.put(key, fiveMap.get(key) / (totalScore - nextId / 2));
            sixMap.put(key, sixMap.get(key) / (totalScore - nextId / 2));
            specialMap.put(key, specialMap.get(key) / (totalScore - nextId / 2));
        }
        predictList.add(oneMap);
        predictList.add(twoMap);
        predictList.add(threeMap);
        predictList.add(fourMap);
        predictList.add(fiveMap);
        predictList.add(sixMap);
        predictList.add(specialMap);
        return predictList;
    }

    @Transactional
    public HashMap<Integer, Double> forecastSpecial() {
        return predictNextLotteryMarkSix().get(6);
    }

    private void doBalanceSpecial(List<RealtimeStat> realtimeStatList, HashMap<Integer, Double> numberMap, int issue) {
        for (RealtimeStat stat : realtimeStatList) {
            double balance = stat.getStakes() * stat.getOdds();
            if (numberMap.get(stat.getNumber()) < 1) {
                stat.setBalance(balance);
            } else {
                stat.setBalance(-balance);
            }
        }
    }

    @Transactional
    public void predictBalance(List<RealtimeStat> realtimeStatList, LotteryMarkSixType type, int issue) {
        switch (type) {
            case SPECIAL:
                doBalanceSpecial(realtimeStatList, forecastSpecial(), issue);
                break;
            case TWO_FACES:
                doBalanceTwoFaces(realtimeStatList, forecastSpecial(), issue);
                break;
            case ZODIAC:
                doBalanceZodiac(realtimeStatList, forecastSpecial(), issue);
                break;
            case HALF_WAVE:
                doBalanceHalfWave(realtimeStatList, forecastSpecial(), issue);
                break;
            case COLOR:
                doBalanceColor(realtimeStatList, forecastSpecial(), issue);
                break;
            default:
                break;
        }

    }

    private void doBalanceTwoFaces(List<RealtimeStat> realtimeStatList, HashMap<Integer, Double> numberMap, int issue) {
        for (RealtimeStat stat : realtimeStatList) {
            double score = 0;
            int count = 0;
            double balance = stat.getStakes() * stat.getOdds();
            switch (LotteryMarkSixType.valueOf(stat.getLotteryMarkSixType())) {
                case DAN:
                    for (int i = 1; i <= 49; i++) {
                        if (i % 2 == 1) {
                            score += numberMap.get(i);
                            count++;
                        }

                    }
                    break;
                case HEDAN:
                    for (int i = 1; i <= 49; i++) {
                        if ((i / 10 + i % 10) == 1) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case WEIDA:
                    for (int i = 1; i <= 49; i++) {
                        if (i % 10 >= 5) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case JIAQIN:
                    for (int i = 1; i <= 49; i++) {
                        LotteryMarkSixType type = BeanHolder.getLotteryService().getZodiac(0, i);
                        if (Arrays.asList(LotteryMarkSixType.ZODIAC_NIU, LotteryMarkSixType.ZODIAC_MA, LotteryMarkSixType.ZODIAC_YANG, LotteryMarkSixType.ZODIAC_JI, LotteryMarkSixType.ZODIAC_GOU, LotteryMarkSixType.ZODIAC_ZHU).contains(type)) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case SHUANG:
                    for (int i = 1; i <= 49; i++) {
                        if (i % 2 == 0) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case HESHUANG:
                    for (int i = 1; i <= 49; i++) {
                        if ((i / 10 + i % 10) == 0) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case WEIXIAO:
                    for (int i = 1; i <= 49; i++) {
                        if (i % 10 <= 4) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case YESHOU:
                    for (int i = 1; i <= 49; i++) {
                        LotteryMarkSixType yeshou = BeanHolder.getLotteryService().getZodiac(0, i);
                        if (Arrays.asList(LotteryMarkSixType.ZODIAC_SHU, LotteryMarkSixType.ZODIAC_HU, LotteryMarkSixType.ZODIAC_LONG, LotteryMarkSixType.ZODIAC_SHE, LotteryMarkSixType.ZODIAC_TU, LotteryMarkSixType.ZODIAC_HOU).contains(yeshou)) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case DA:
                    for (int i = 1; i <= 49; i++) {
                        if (i >= 25) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case HEDA:
                    for (int i = 1; i <= 49; i++) {
                        if ((i / 10 + i % 10) >= 7) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case HEWEIDA:
                    for (int i = 1; i <= 49; i++) {
                        if ((i / 10 + i % 10) % 10 >= 5) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case XIAO:
                    for (int i = 1; i <= 49; i++) {
                        if (i <= 24) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case HEXIAO:
                    for (int i = 1; i <= 49; i++) {
                        if ((i / 10 + i % 10) <= 6) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case HEWEIXIAO:
                    for (int i = 1; i <= 49; i++) {
                        if ((i / 10 + i % 10) % 10 <= 4) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
            }
            stat.setBalance(score / count > 1 ? -balance : balance);
        }
    }

    private void doBalanceZodiac(List<RealtimeStat> realtimeStatList, HashMap<Integer, Double> numberMap, int issue) {

        for (RealtimeStat stat : realtimeStatList) {
            double score = 0;
            int count = 0;
            double balance = stat.getStakes() * stat.getOdds();
            LotteryMarkSixType type = LotteryMarkSixType.valueOf(stat.getLotteryMarkSixType());

            for (int i = 1; i <= 49; i++) {
                LotteryMarkSixType zodiac = BeanHolder.getLotteryService().getZodiac(0, i);
                if (type.equals(zodiac)) {
                    score += numberMap.get(i);
                    count++;
                }
            }
            stat.setBalance(score / count > 1 ? -balance : balance);

        }
    }

    private void doBalanceHalfWave(List<RealtimeStat> realtimeStatList, HashMap<Integer, Double> numberMap, int issue) {
        for (RealtimeStat stat : realtimeStatList) {
            double score = 0;
            int count = 0;
            double balance = stat.getStakes() * stat.getOdds();
            switch (LotteryMarkSixType.valueOf(stat.getLotteryMarkSixType())) {
                case WAVE_RED_DA:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.RED) && i >= 25) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case WAVE_RED_DAN:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.RED) && i % 2 == 1) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case WAVE_RED_XIAO:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.RED) && i <= 24) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case WAVE_RED_SHUANG:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.RED) && i % 2 == 0) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case WAVE_BLUE_DA:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.BLUE) && i >= 25) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case WAVE_BLUE_DAN:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.BLUE) && i % 2 == 1) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case WAVE_BLUE_XIAO:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.BLUE) && i <= 24) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case WAVE_BLUE_SHUANG:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.BLUE) && i % 2 == 0) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case WAVE_GREEN_DA:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.GREEN) && i >= 25) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case WAVE_GREEN_DAN:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.GREEN) && i % 2 == 1) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case WAVE_GREEN_XIAO:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.GREEN) && i <= 24) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
                case WAVE_GREEN_SHUANG:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.GREEN) && i % 2 == 0) {
                            score += numberMap.get(i);
                            count++;
                        }
                    }
                    break;
            }
            stat.setBalance(score / count > 1 ? -balance : balance);
        }
    }

    private void doBalanceColor(List<RealtimeStat> realtimeStatList, HashMap<Integer, Double> numberMap, int issue) {
        for (RealtimeStat stat : realtimeStatList) {
            double score = 0;
            int count = 0;
            double balance = stat.getStakes() * stat.getOdds();
            switch (LotteryMarkSixType.valueOf(stat.getLotteryMarkSixType())) {
                case RED:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.RED)) {
                            score += numberMap.get(i);
                            count++;
                        }

                    }
                    break;
                case BLUE:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.BLUE)) {
                            score += numberMap.get(i);
                            count++;
                        }

                    }
                    break;
                case GREEN:
                    for (int i = 1; i <= 49; i++) {
                        if (LotteryBall.valueOf("NUM_" + i).getColor().equals(MarkSixColor.GREEN)) {
                            score += numberMap.get(i);
                            count++;
                        }

                    }
                    break;
            }
            stat.setBalance(score / count > 1 ? -balance : balance);
        }
    }
}
