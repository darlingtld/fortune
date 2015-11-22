package fortune.pojo;

import java.util.Date;

/**
 * Created by lingda on 2015/11/22.
 */
public class RealTimeWager {
    private Date ts;
    private User user;
    private double tuishui;
    private String panlei;
    private int issue;
    private Date openTs;
    private String wageContent;
    private double stakes;
    private double odds;
    private double tuishui2;
    private double result;
    private String remark;

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTuishui() {
        return tuishui;
    }

    public void setTuishui(double tuishui) {
        this.tuishui = tuishui;
    }

    public String getPanlei() {
        return panlei;
    }

    public void setPanlei(String panlei) {
        this.panlei = panlei;
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public Date getOpenTs() {
        return openTs;
    }

    public void setOpenTs(Date openTs) {
        this.openTs = openTs;
    }

    public String getWageContent() {
        return wageContent;
    }

    public void setWageContent(String wageContent) {
        this.wageContent = wageContent;
    }

    public double getStakes() {
        return stakes;
    }

    public void setStakes(double stakes) {
        this.stakes = stakes;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    public double getTuishui2() {
        return tuishui2;
    }

    public void setTuishui2(double tuishui2) {
        this.tuishui2 = tuishui2;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
