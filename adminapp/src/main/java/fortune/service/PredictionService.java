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
        double totalStakes4Type = 0;
        for (RealtimeStat stat : realtimeStatList) {
            totalStakes4Type += stat.getStakes();
        }
        for (RealtimeStat stat : realtimeStatList) {
            double balance = totalStakes4Type - stat.getStakes() * stat.getOdds();
            stat.setBalance(balance);
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
            case SUM_ZODIAC:
                doBalanceSumZodiac(realtimeStatList, forecastSpecial(), issue);
                break;
            case ZHENG_BALL:
                doBalanceZhengBall(realtimeStatList, null, issue);
                break;
            case ZHENG_1_6:
                doBalanceZheng16(realtimeStatList, null, issue);
                break;
            case ZHENG_SPECIFIC_1:
            case ZHENG_SPECIFIC_2:
            case ZHENG_SPECIFIC_3:
            case ZHENG_SPECIFIC_4:
            case ZHENG_SPECIFIC_5:
            case ZHENG_SPECIFIC_6:
                doBalanceZhengSpecific(realtimeStatList, type, issue);
                break;
            case ONE_ZODIAC:
                doBalanceOneZodiac(realtimeStatList, issue);
                break;
            case TAIL_NUM:
                doBalanceTailNum(realtimeStatList, issue);
                break;
            default:
                break;
        }

    }

    private void doBalanceTailNum(List<RealtimeStat> realtimeStatList, int issue) {
        double totalStakes4Type = 0;
        for (RealtimeStat stat : realtimeStatList) {
            totalStakes4Type += stat.getStakes();
        }

        for (RealtimeStat stat : realtimeStatList) {
            double balance = totalStakes4Type - stat.getStakes() * stat.getOdds();

            stat.setBalance(balance);

        }
    }

    private void doBalanceOneZodiac(List<RealtimeStat> realtimeStatList, int issue) {
        double totalStakes4Type = 0;
        for (RealtimeStat stat : realtimeStatList) {
            totalStakes4Type += stat.getStakes();
        }

        for (RealtimeStat stat : realtimeStatList) {
            double balance = totalStakes4Type - stat.getStakes() * stat.getOdds();

            stat.setBalance(balance);

        }

    }

    private void doBalanceZhengSpecific(List<RealtimeStat> realtimeStatList, LotteryMarkSixType type, int issue) {
        double totalStakes4Type = 0;
        for (RealtimeStat stat : realtimeStatList) {
            totalStakes4Type += stat.getStakes();
        }
        for (RealtimeStat stat : realtimeStatList) {

            double balance = totalStakes4Type - stat.getStakes() * stat.getOdds();
            stat.setBalance(balance);
        }
    }

    private void doBalanceZheng16(List<RealtimeStat> realtimeStatList, HashMap map, int issue) {
        double totalStakes4Type = 0;
        for (RealtimeStat stat : realtimeStatList) {
            totalStakes4Type += stat.getStakes();
        }

        for (RealtimeStat stat : realtimeStatList) {

            double balance = totalStakes4Type - stat.getStakes() * stat.getOdds();

            stat.setBalance(balance);

        }
    }

    private void doBalanceZhengBall(List<RealtimeStat> realtimeStatList, HashMap map, int issue) {
        double totalStakes4Type = 0;
        for (RealtimeStat stat : realtimeStatList) {
            totalStakes4Type += stat.getStakes();
        }
        for (RealtimeStat stat : realtimeStatList) {

            double balance = totalStakes4Type - stat.getStakes() * stat.getOdds();

            stat.setBalance(balance);

        }
    }

    private void doBalanceTwoFaces(List<RealtimeStat> realtimeStatList, HashMap<Integer, Double> numberMap, int issue) {
        double totalStakes4Type = 0;
        for (RealtimeStat stat : realtimeStatList) {
            totalStakes4Type += stat.getStakes();
        }
        for (RealtimeStat stat : realtimeStatList) {
            double balance = totalStakes4Type - stat.getStakes() * stat.getOdds();
            stat.setBalance(balance);
        }
    }

    private void doBalanceZodiac(List<RealtimeStat> realtimeStatList, HashMap<Integer, Double> numberMap, int issue) {
        double totalStakes4Type = 0;
        for (RealtimeStat stat : realtimeStatList) {
            totalStakes4Type += stat.getStakes();
        }
        for (RealtimeStat stat : realtimeStatList) {
            double balance = totalStakes4Type - stat.getStakes() * stat.getOdds();
            stat.setBalance(balance);
        }
    }

    private void doBalanceHalfWave(List<RealtimeStat> realtimeStatList, HashMap<Integer, Double> numberMap, int issue) {
        double totalStakes4Type = 0;
        for (RealtimeStat stat : realtimeStatList) {
            totalStakes4Type += stat.getStakes();
        }
        for (RealtimeStat stat : realtimeStatList) {
            double balance = totalStakes4Type - stat.getStakes() * stat.getOdds();
            stat.setBalance(balance);
        }
    }

    private void doBalanceColor(List<RealtimeStat> realtimeStatList, HashMap<Integer, Double> numberMap, int issue) {
        double totalStakes4Type = 0;
        for (RealtimeStat stat : realtimeStatList) {
            totalStakes4Type += stat.getStakes();
        }
        for (RealtimeStat stat : realtimeStatList) {

            double balance = totalStakes4Type - stat.getStakes() * stat.getOdds();

            stat.setBalance(balance);
        }
    }

    private void doBalanceSumZodiac(List<RealtimeStat> realtimeStatList, HashMap<Integer, Double> numberMap, int issue) {
        double totalStakes4Type = 0;
        for (RealtimeStat stat : realtimeStatList) {
            totalStakes4Type += stat.getStakes();
        }
        for (RealtimeStat stat : realtimeStatList) {
            double balance = totalStakes4Type - stat.getStakes() * stat.getOdds();
            stat.setBalance(balance);
        }
    }
}
