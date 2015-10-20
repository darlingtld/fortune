package fortune.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by darlingtld on 2015/10/20 0020.
 */
@Document(collection = "lottery_mark_six_wager")
public class LotteryMarkSixWager {

    @Id
    private String id;

    private List<WagerStub> wagerStubList;

    class WagerStub {
        private int number;
        private double stakes;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public double getStakes() {
            return stakes;
        }

        public void setStakes(double stakes) {
            this.stakes = stakes;
        }
    }
}
