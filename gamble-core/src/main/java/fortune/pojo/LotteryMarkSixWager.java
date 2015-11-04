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

    private int lotteryIssue;

    @NotNull
    private String pgroupId;

    @NotNull
    private List<LotteryMarkSixWagerStub> lotteryMarkSixWagerStubList;

    private double totalStakes;

    @NotNull
    private LotteryMarkSixType lotteryMarkSixType;

    @Override
    public String toString() {
        return "LotteryMarkSixWager{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", timestamp=" + timestamp +
                ", lotteryIssue=" + lotteryIssue +
                ", pgroupId=" + pgroupId +
                ", lotteryMarkSixWagerStubList=" + lotteryMarkSixWagerStubList +
                ", totalStakes=" + totalStakes +
                ", lotteryMarkSixType=" + lotteryMarkSixType +
                '}';
    }

    public LotteryMarkSixWager() {
    }

    public LotteryMarkSixWager(int userId, String pgroupId, int lotteryIssue, double totalStakes, List<LotteryMarkSixWagerStub> lotteryMarkSixWagerStubList, LotteryMarkSixType type) {
        this.userId = userId;
        this.pgroupId = pgroupId;
        this.lotteryIssue = lotteryIssue;
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

    public String getPgroupId() {
        return pgroupId;
    }

    public void setPgroupId(String pgroupId) {
        this.pgroupId = pgroupId;
    }

    public double getTotalStakes() {
        return totalStakes;
    }

    public void setTotalStakes(double totalStakes) {
        this.totalStakes = totalStakes;
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
