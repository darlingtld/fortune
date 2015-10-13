package fortune.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by tangl9 on 2015-10-13.
 */
@Entity
@Table(name = "pgroup")
public class PGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "t_user_pgroup", joinColumns = {@JoinColumn(name = "pgroupid")}, inverseJoinColumns = {@JoinColumn(name = "userid")})
    private Set<User> users;

    @Override
    public String toString() {
        return "PGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
