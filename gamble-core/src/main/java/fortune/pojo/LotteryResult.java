package fortune.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;

/**
 * Created by lingda on 2015/10/24.
 */
@Document(collection = "lottery_result")
public class LotteryResult {

    @Id
    private String id;
    private String userId;
    private String groupId;
    private int lotteryIssue;
    private double winningMoney;
    private String lotteryMarkSixWagerId;

    @Override
    public String toString() {
        return "LotteryResult{" +
                "id=" + id +
                ", userId=" + userId +
                ", groupId=" + groupId +
                ", lotteryIssue=" + lotteryIssue +
                ", winningMoney=" + winningMoney +
                ", lotteryMarkSixWagerId=" + lotteryMarkSixWagerId +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public double getWinningMoney() {
        return winningMoney;
    }

    public void setWinningMoney(double winningMoney) {
        this.winningMoney = winningMoney;
    }

    public String getLotteryMarkSixWagerId() {
        return lotteryMarkSixWagerId;
    }

    public void setLotteryMarkSixWagerId(String lotteryMarkSixWagerId) {
        this.lotteryMarkSixWagerId = lotteryMarkSixWagerId;
    }
}
