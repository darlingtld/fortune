package fortune.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

/**
 * Created by tangl9 on 2015-10-21.
 */
@Document(collection = "lottery_odds")
public class LotteryOdds {

    @Id
    private String id;

    private int lotteryBallNumber;

    private double odds;

    private int groupId;

    private int lotteryIssue;

    private Date timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LotteryOdds{" +
                "id='" + id + '\'' +
                ", lotteryBallNumber=" + lotteryBallNumber +
                ", odds=" + odds +
                ", groupId=" + groupId +
                ", lotteryIssue=" + lotteryIssue +
                ", timestamp=" + timestamp +
                '}';
    }

    public int getLotteryBallNumber() {
        return lotteryBallNumber;
    }

    public void setLotteryBallNumber(int lotteryBallNumber) {
        this.lotteryBallNumber = lotteryBallNumber;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getLotteryIssue() {
        return lotteryIssue;
    }

    public void setLotteryIssue(int lotteryIssue) {
        this.lotteryIssue = lotteryIssue;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
