package other;

import fortune.pojo.LotteryBall;

/**
 * Created by tangl9 on 2015-10-21.
 */
public class test {
    public static void main(String[] args) {
        String ball = "NUM_1";
        LotteryBall lotteryBall = LotteryBall.valueOf(ball);
        System.out.println(lotteryBall);
    }
}
