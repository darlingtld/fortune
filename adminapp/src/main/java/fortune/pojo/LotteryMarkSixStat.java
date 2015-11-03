package fortune.pojo;

import javax.persistence.*;

/**
 * Created by tangl9 on 2015-11-03.
 */
@Entity
@Table(name = "lottery_mark_six_stat")
public class LotteryMarkSixStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lottery_mark_six_id")
    private LotteryMarkSix lotteryMarkSix;
    @Column(name = "pgroup_id")
    private String pgroupId;
    @Column(name = "total_stakes")
    private double totalStakes;
    @Column(name = "user_result")
    private double userResult;
    @Column(name = "pgroup_result")
    private double pgroupResult;
    @Column(name = "zoufei_stakes")
    private double zoufeiStakes;
    @Column(name = "zoufei_result")
    private double zoufeiResult;
    @Column(name = "pgroup_total_result")
    private double pgroupTotalResult;
    @Column(name = "remark")
    private String remark;

    @Override
    public String toString() {
        return "LotteryMarkSixStat{" +
                "id='" + id + '\'' +
                ", lotteryMarkSix=" + lotteryMarkSix +
                ", pgroupId='" + pgroupId + '\'' +
                ", totalStakes=" + totalStakes +
                ", userResult=" + userResult +
                ", pgroupResult=" + pgroupResult +
                ", zoufeiStakes=" + zoufeiStakes +
                ", zoufeiResult=" + zoufeiResult +
                ", pgroupTotalResult=" + pgroupTotalResult +
                ", remark='" + remark + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LotteryMarkSix getLotteryMarkSix() {
        return lotteryMarkSix;
    }

    public void setLotteryMarkSix(LotteryMarkSix lotteryMarkSix) {
        this.lotteryMarkSix = lotteryMarkSix;
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

    public double getUserResult() {
        return userResult;
    }

    public void setUserResult(double userResult) {
        this.userResult = userResult;
    }

    public double getPgroupResult() {
        return pgroupResult;
    }

    public void setPgroupResult(double pgroupResult) {
        this.pgroupResult = pgroupResult;
    }

    public double getZoufeiStakes() {
        return zoufeiStakes;
    }

    public void setZoufeiStakes(double zoufeiStakes) {
        this.zoufeiStakes = zoufeiStakes;
    }

    public double getZoufeiResult() {
        return zoufeiResult;
    }

    public void setZoufeiResult(double zoufeiResult) {
        this.zoufeiResult = zoufeiResult;
    }

    public double getPgroupTotalResult() {
        return pgroupTotalResult;
    }

    public void setPgroupTotalResult(double pgroupTotalResult) {
        this.pgroupTotalResult = pgroupTotalResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
