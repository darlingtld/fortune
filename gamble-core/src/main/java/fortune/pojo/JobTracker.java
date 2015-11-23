package fortune.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tangl9 on 2015-11-23.
 */
@Entity
@Table(name = "job_tracker")
public class JobTracker {
    public static final String RUNNING = "RUNNING";
    public static final String FAILED = "FAILED";
    public static final String SUCCESS = "SUCCESS";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "issue")
    private int issue;
    @Column(name = "starttime")
    private Date startTime;
    @Column(name = "endtime")
    private Date endTime;
    @Column(name = "status")
    private String status;

    public JobTracker() {
    }

    public JobTracker(String name, int lotteryIssue, Date date, String status) {
        this.name = name;
        this.issue = lotteryIssue;
        this.startTime = date;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
