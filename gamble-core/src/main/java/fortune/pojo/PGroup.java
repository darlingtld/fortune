package fortune.pojo;

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

    private List<PGroup> subPGroupList;

    private List<User> userList;

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

    @Override
    public String toString() {
        return "PGroup{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", subPGroupList=" + subPGroupList +
                ", userList=" + userList +
                '}';
    }

    public List<PGroup> getSubPGroupList() {
        return subPGroupList;
    }

    public void setSubPGroupList(List<PGroup> subPGroupList) {
        this.subPGroupList = subPGroupList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
