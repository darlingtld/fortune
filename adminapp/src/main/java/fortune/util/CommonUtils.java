package fortune.util;

import fortune.pojo.LotteryMarkSixWager;

public class CommonUtils {

    public static int getTransactionsOfWager(LotteryMarkSixWager wager) {
        int transactionNum = 0;
        switch (wager.getLotteryMarkSixType()) {
            case JOINT_3_ALL:
            case JOINT_3_2:
            case JOINT_2_ALL:
            case JOINT_2_SPECIAL:
            case JOINT_SPECIAL:
            case NOT_5:
            case NOT_6:
            case NOT_7:
            case NOT_8:
            case NOT_9:
            case NOT_10:
            case NOT_11:
            case NOT_12:
            case WAVE_RED_DA:
            case WAVE_RED_XIAO:
            case WAVE_RED_DAN:
            case WAVE_RED_SHUANG:
            case WAVE_BLUE_DA:
            case WAVE_BLUE_XIAO:
            case WAVE_BLUE_DAN:
            case WAVE_BLUE_SHUANG:
            case WAVE_GREEN_DA:
            case WAVE_GREEN_XIAO:
            case WAVE_GREEN_DAN:
            case WAVE_GREEN_SHUANG:
            case JOINT_ZODIAC_PING:
            case JOINT_ZODIAC_ZHENG:
            case JOINT_TAIL_2:
            case JOINT_TAIL_3:
            case JOINT_TAIL_4:
            case JOINT_TAIL_NOT_2:
            case JOINT_TAIL_NOT_3:
            case JOINT_TAIL_NOT_4:
                transactionNum = 1;
                break;
            default:
                if (wager.getLotteryMarkSixWagerStubList() != null) {
                    transactionNum = wager.getLotteryMarkSixWagerStubList().size();
                }
        }
        
        return transactionNum;
    }
}
