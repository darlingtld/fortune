package fortune.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by jason on 2015-01-06.
 */
@Document(collection = "lottery_tuishui")
public class LotteryTuishui {

    @Id
    private String id;

    @NotNull
    private String userId;
    
    @NotNull
    private double tuishui;

    @NotNull
    private String groupId;

    @NotNull
    private String panlei;
    
    @NotNull
    private Date timestamp = new Date();
    
    private int lotteryBallNumber;

    // 一级类型
    private LotteryMarkSixType lotteryMarkSixType;
    
    // 二级类型，如正码1～6下的子类型，一肖下的生肖，连肖下面的生肖
    private LotteryMarkSixType lotteryBallType; // (名字不改了)

    public LotteryTuishui() {}

    public LotteryTuishui(int lotteryBallNumber, LotteryMarkSixType lotteryBallType, double tuishui, String groupId, String userId, 
                       Date timestamp, LotteryMarkSixType lotteryMarkSixType, String panlei) {
        this.userId = userId;
        this.lotteryBallNumber = lotteryBallNumber;
        this.lotteryBallType = lotteryBallType;
        this.tuishui = tuishui;
        this.groupId = groupId;
        this.timestamp = timestamp;
        this.lotteryMarkSixType = lotteryMarkSixType;
        this.panlei = panlei;
    }

    @Override
    public String toString() {
        return "LotteryTuishui{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", lotteryBallNumber=" + lotteryBallNumber +
                ", tuishui=" + tuishui +
                ", groupId='" + groupId + '\'' +
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

    public double getTuishui() {
        return tuishui;
    }

    public void setTuishui(double tuishui) {
        this.tuishui = tuishui;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
