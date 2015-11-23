package fortune.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tangl9 on 2015-11-23.
 */
@Entity
@Table(name = "lottery_mark_six_user_stat")
public class LotteryMarkSixUserStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "open_ts")
    private Date openTs;
    @Column(name = "issue")
    private int issue;
    @Column(name = "stakes")
    private double stakes;
    @Column(name = "valid_stakes")
    private double validStakes;
    @Column(name = "tuishui")
    private double tuishui;
    @Column(name = "result")
    private double result;
    @Column(name = "userid")
    private String userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getOpenTs() {
        return openTs;
    }

    public void setOpenTs(Date openTs) {
        this.openTs = openTs;
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public double getStakes() {
        return stakes;
    }

    public void setStakes(double stakes) {
        this.stakes = stakes;
    }

    public double getValidStakes() {
        return validStakes;
    }

    public void setValidStakes(double validStakes) {
        this.validStakes = validStakes;
    }

    public double getTuishui() {
        return tuishui;
    }

    public void setTuishui(double tuishui) {
        this.tuishui = tuishui;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
