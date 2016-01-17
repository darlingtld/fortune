package fortune.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by tangl9 on 2015-10-13.
 */
@Document(collection = "pgroup")
public class PGroup {
    @Id
    private String id;
    @NotNull
    private String name;

    private String parentPGroupID;

    private User admin;

    private List<User> userList = new ArrayList<>();

    //	最大输赢金额总数，默认50000
    private double maxStakes = 50000;
    //	被父代理商占成比，默认为0
    private double zoufeiBy = 0;
    //	占据下属代理商的走飞
    private List<Zoufei> zoufeiList = new ArrayList<>();

    public double getZoufeiBy() {
        return zoufeiBy;
    }

    public void setZoufeiBy(double zoufeiBy) {
        this.zoufeiBy = zoufeiBy;
    }

    public double getMaxStakes() {
        return maxStakes;
    }

    public void setMaxStakes(double maxStakes) {
        this.maxStakes = maxStakes;
    }

    public List<Zoufei> getZoufeiList() {
        return zoufeiList;
    }

    public void setZoufeiList(List<Zoufei> zoufeiList) {
        this.zoufeiList = zoufeiList;
    }

    @Override
    public String toString() {
        return "PGroup{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", admin=" + admin + ", userList=" + userList + '}';
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getParentPGroupID() {
        return parentPGroupID;
    }

    public void setParentPGroupID(String parentPGroupID) {
        this.parentPGroupID = parentPGroupID;
    }
}
