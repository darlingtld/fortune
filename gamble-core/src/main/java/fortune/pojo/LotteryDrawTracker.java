package fortune.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tangl9 on 2015-11-23.
 */
@Entity
@Table(name = "lottery_draw_tracker")
public class LotteryDrawTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "last_lottery_issue")
    private int lastLotteryIssue;
    @Column(name = "timestamp")
    private Date timestamp;

    public LotteryDrawTracker() {
    }

    public LotteryDrawTracker(int latestIssue, Date date) {
        this.lastLotteryIssue = latestIssue;
        this.timestamp = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLastLotteryIssue() {
        return lastLotteryIssue;
    }

    public void setLastLotteryIssue(int lastLotteryIssue) {
        this.lastLotteryIssue = lastLotteryIssue;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
