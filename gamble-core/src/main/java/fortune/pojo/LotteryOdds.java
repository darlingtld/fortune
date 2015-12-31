package fortune.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by tangl9 on 2015-10-21.
 */
@Document(collection = "lottery_odds")
public class LotteryOdds {

    @Id
    private String id;

    private int lotteryBallNumber; // 在连肖中，也表示几连肖；连尾中，表示0-－9

    @NotNull
    private double odds;

    @NotNull
    private String groupId;

    @NotNull
    private int lotteryIssue;
    @NotNull
    private String panlei;
    @NotNull
    private Date timestamp = new Date();
    // 一级类型
    private LotteryMarkSixType lotteryMarkSixType;
    // 二级类型，如正码1～6下的子类型，一肖下的生肖，连肖下面的生肖
    private LotteryMarkSixType lotteryBallType; // (名字不改了)

    public LotteryOdds() {
    }

    public LotteryOdds(int lotteryBallNumber, LotteryMarkSixType lotteryBallType, double odds, String groupId,
                       int lotteryIssue, Date timestamp, LotteryMarkSixType lotteryMarkSixType, String panlei) {
        this.lotteryBallNumber = lotteryBallNumber;
        this.lotteryBallType = lotteryBallType;
        this.odds = odds;
        this.groupId = groupId;
        this.lotteryIssue = lotteryIssue;
        this.timestamp = timestamp;
        this.lotteryMarkSixType = lotteryMarkSixType;
        this.panlei = panlei;
    }

    @Override
    public String toString() {
        return "LotteryOdds{" +
                "id='" + id + '\'' +
                ", lotteryBallNumber=" + lotteryBallNumber +
                ", odds=" + odds +
                ", groupId='" + groupId + '\'' +
                ", lotteryIssue=" + lotteryIssue +
                ", panlei='" + panlei + '\'' +
                ", timestamp=" + timestamp +
                ", lotteryMarkSixType=" + lotteryMarkSixType +
                ", lotteryBallType=" + lotteryBallType +
                '}';
    }

    public String getPanlei() {
        return panlei;
    }

    public void setPanlei(String panlei) {
        this.panlei = panlei;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
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

    public LotteryMarkSixType getLotteryMarkSixType() {
        return lotteryMarkSixType;
    }

    public void setLotteryMarkSixType(LotteryMarkSixType lotteryMarkSixType) {
        this.lotteryMarkSixType = lotteryMarkSixType;
    }

    public LotteryMarkSixType getLotteryBallType() {
        return lotteryBallType;
    }

    public void setLotteryBallType(LotteryMarkSixType lotteryBallType) {
        this.lotteryBallType = lotteryBallType;
    }
}
