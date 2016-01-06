package fortune.pojo;

import java.util.Date;

/**
 * Created by tangl9 on 2015-11-05.
 */
public class NextLotteryMarkSixInfo {

    private int issue;
    private Date date;
    private Date wageDate;

    public Date getWageDate() {
        return wageDate;
    }

    public void setWageDate(Date wageDate) {
        this.wageDate = wageDate;
    }

    @Override
    public String toString() {
        return "NextLotteryMarkSixInfo{" +
                "issue=" + issue +
                ", date=" + date +
                ", wageDate=" + wageDate +
                '}';
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
