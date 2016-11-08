
package rs.goran.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.log4j.Logger;

@Entity
public class Team {

    private static final Logger logger = Logger.getLogger(Team.class);

    @Id
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_team", joinColumns = @JoinColumn(name = "team_name"), inverseJoinColumns = @JoinColumn(name = "user_email"))
    private Set<User> users = new HashSet<>();

    private String creator;

    public Team() {

    }

    public Team(String name, String creator) {
        this.name = name;
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    protected boolean addUser(User user) {
        if (!this.users.contains(user)) {
            this.users.add(user);
            logger.info("User " + user.getName() + " added.");
            return true;
        } else {
            logger.warn("User " + user.getName() + " already exists in database.");
            return false;
        }
    }

    protected boolean removeUser(User user) {
        if (this.users.contains(user)) {
            this.users.remove(user);
            logger.info("User " + user.getName() + " deleted.");
            return true;
        } else {
            logger.warn("User " + user.getName() + " does not exist.");
            return false;
        }
    }

}
