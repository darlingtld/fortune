package fortune.pojo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by darlingtld on 2015/10/6 0006.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private Set<PGroup> PGroups;
    @Column(name = "account")
    private double account;
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", PGroups=" + PGroups +
                ", account=" + account +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }

    public Set<PGroup> getPGroups() {
        return PGroups;
    }

    public void setPGroups(Set<PGroup> PGroups) {
        this.PGroups = PGroups;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }
}
