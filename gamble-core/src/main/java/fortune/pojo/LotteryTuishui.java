package fortune.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;
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
    
    // 一级类型
    private LotteryMarkSixType lotteryMarkSixType;
    
    // 二级类型，如正码1～6下的子类型，一肖下的生肖，连肖下面的生肖
    private LotteryMarkSixType lotteryBallType;
    
    @Transient
    private String lotteryTypeName;

    @Override
    public String toString() {
        return "LotteryTuishui{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
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
    
    public String getLotteryTypeName() {
        return lotteryTypeName;
    }

    public void setLotteryTypeName(String lotteryTypeName) {
        this.lotteryTypeName = lotteryTypeName;
    }
}
