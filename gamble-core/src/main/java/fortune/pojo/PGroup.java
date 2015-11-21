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
