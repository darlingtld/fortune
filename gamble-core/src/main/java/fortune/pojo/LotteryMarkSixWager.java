package fortune.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by darlingtld on 2015/10/20 0020.
 */
@Document(collection = "lottery_mark_six_wager")
public class LotteryMarkSixWager {

    @Id
    private String id;

    private int userId;

    private Date timestamp;

    private int lotteryIssue;

    private List<LotteryMarkSixWagerStub> lotteryMarkSixWagerStubList;

    private double totalMoney;

    private double totalStakes;

    public LotteryMarkSixWager() {
    }

    public LotteryMarkSixWager(int userId, int lotteryIssue, double totalMoney, double totalStakes, List<LotteryMarkSixWagerStub> lotteryMarkSixWagerStubList) {
        this.userId = userId;
        this.lotteryIssue = lotteryIssue;
        this.totalMoney = totalMoney;
        this.totalStakes = totalStakes;
        this.lotteryMarkSixWagerStubList = lotteryMarkSixWagerStubList;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getTotalStakes() {
        return totalStakes;
    }

    public void setTotalStakes(double totalStakes) {
        this.totalStakes = totalStakes;
    }

    @Override
    public String toString() {
        return "LotteryMarkSixWager{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", timestamp=" + timestamp +
                ", lotteryIssue=" + lotteryIssue +
                ", lotteryMarkSixWagerStubList=" + lotteryMarkSixWagerStubList +
                ", totalMoney=" + totalMoney +
                ", totalStakes=" + totalStakes +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getLotteryIssue() {
        return lotteryIssue;
    }

    public void setLotteryIssue(int lotteryIssue) {
        this.lotteryIssue = lotteryIssue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LotteryMarkSixWagerStub> getLotteryMarkSixWagerStubList() {
        return lotteryMarkSixWagerStubList;
    }

    public void setLotteryMarkSixWagerStubList(List<LotteryMarkSixWagerStub> lotteryMarkSixWagerStubList) {
        this.lotteryMarkSixWagerStubList = lotteryMarkSixWagerStubList;
    }
}
