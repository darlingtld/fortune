package fortune.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by darlingtld on 2015/10/6 0006.
 */
@Document(collection = "user")
public class User {
	@Id
	private String id;
	private String username;
	private String password;
	private List<Role> roleList = new ArrayList<>();
	private List<PGroup> pGroupList = new ArrayList<>();
	private double creditAccount;
	private double usedCreditAccount;
	private Date lastLoginTime;
	private PeopleStatus status = PeopleStatus.ENABLED;
	private boolean canDelete;

	public double getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(double creditAccount) {
		this.creditAccount = creditAccount;
	}

	public double getUsedCreditAccount() {
		return usedCreditAccount;
	}

	public void setUsedCreditAccount(double usedCreditAccount) {
		this.usedCreditAccount = usedCreditAccount;
	}

	public List<PGroup> getpGroupList() {
		return pGroupList;
	}

	public void setpGroupList(List<PGroup> pGroupList) {
		this.pGroupList = pGroupList;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public PeopleStatus getStatus() {
		return status;
	}

	public void setStatus(PeopleStatus status) {
		this.status = status;
	}
	
	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	@Override
	public String toString() {
		return "User{" + "id='" + id + '\'' + ", username='" + username + '\'' + ", password='" + password + '\''
				+ ", roleList=" + roleList + ", pGroupList=" + pGroupList + ", creditAccount=" + creditAccount
				+ ", usedCreditAccount=" + usedCreditAccount + ", lastLoginTime=" + lastLoginTime + '}';
	}
}
