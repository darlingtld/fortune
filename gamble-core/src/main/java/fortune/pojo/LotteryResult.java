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
    private int id;
    private int userId;
    private int groupId;
    private int lotteryIssue;
    private double winningMoney;
    private List<String> lotteryMarkSixWagerList;

    @Override
    public String toString() {
        return "LotteryResult{" +
                "id=" + id +
                ", userId=" + userId +
                ", groupId=" + groupId +
                ", lotteryIssue=" + lotteryIssue +
                ", winningMoney=" + winningMoney +
                ", lotteryMarkSixWagerList=" + lotteryMarkSixWagerList +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public double getWinningMoney() {
        return winningMoney;
    }

    public void setWinningMoney(double winningMoney) {
        this.winningMoney = winningMoney;
    }

    public List<String> getLotteryMarkSixWagerList() {
        return lotteryMarkSixWagerList;
    }

    public void setLotteryMarkSixWagerList(List<String> lotteryMarkSixWagerList) {
        this.lotteryMarkSixWagerList = lotteryMarkSixWagerList;
    }
}
