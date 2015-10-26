package fortune.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by darlingtld on 2015/10/20 0020.
 */
@Document(collection = "lottery_mark_six_wager")
public class LotteryMarkSixWager {

    @Id
    private String id;

    @NotNull
    private int userId;

    @NotNull
    private Date timestamp = new Date();

    @NotNull
    private int lotteryIssue;

    @NotNull
    private int pgroupId;

    @NotNull
    private List<LotteryMarkSixWagerStub> lotteryMarkSixWagerStubList;

    private double totalMoney;

    private double totalStakes;

    @NotNull
    private LotteryMarkSixType lotteryMarkSixType;

    public LotteryMarkSixWager() {
    }

    public LotteryMarkSixWager(int userId, int pgroupId, int lotteryIssue, double totalMoney, double totalStakes, List<LotteryMarkSixWagerStub> lotteryMarkSixWagerStubList, LotteryMarkSixType type) {
        this.userId = userId;
        this.pgroupId = pgroupId;
        this.lotteryIssue = lotteryIssue;
        this.totalMoney = totalMoney;
        this.totalStakes = totalStakes;
        this.lotteryMarkSixWagerStubList = lotteryMarkSixWagerStubList;
        this.lotteryMarkSixType = type;
    }

    public LotteryMarkSixType getLotteryMarkSixType() {
        return lotteryMarkSixType;
    }

    public void setLotteryMarkSixType(LotteryMarkSixType lotteryMarkSixType) {
        this.lotteryMarkSixType = lotteryMarkSixType;
    }

    public int getPgroupId() {
        return pgroupId;
    }

    public void setPgroupId(int pgroupId) {
        this.pgroupId = pgroupId;
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
