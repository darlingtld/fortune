package fortune.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tangl9 on 2015-12-21.
 */
@Entity
@Table(name = "action_trace")
public class ActionTrace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user")
    private String user;
    @Column(name = "action")
    private String action;
    @Column(name = "timestamp")
    private Date timestamp;
    @Column(name = "request_url")
    private String requestUrl;
    @Column(name = "ip")
    private String ip;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
