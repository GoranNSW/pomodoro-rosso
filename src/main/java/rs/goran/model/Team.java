
package rs.goran.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.Session;

import rs.goran.service.HibernateUtil;

@Entity
public class Team {

    @Id
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_team", joinColumns = @JoinColumn(name = "team_name"), inverseJoinColumns = @JoinColumn(name = "user_email"))
    private Set<User> userList = new HashSet<>();

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

    public Set<User> getUserList() {
        return userList;
    }

    public void setUserList(Set<User> users) {
        this.userList = users;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean addUser(User user) {
        if (!this.getUserList().contains(user)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            this.getUserList().add(user);
            session.saveOrUpdate(this);
            session.getTransaction().commit();
            session.close();
            System.out.println("User " + user.getName() + " added.");
            return true;
        } else {
            System.out.println("User " + user.getName() + " already exists in database.");
            return false;
        }
    }

    public boolean removeUser(User user) {
        if (this.getUserList().contains(user)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            this.getUserList().remove(user);
            session.saveOrUpdate(this);
            session.getTransaction().commit();
            session.close();
            System.out.println("User " + user.getName() + " deleted.");
            return true;
        } else {
            System.out.println("User " + user.getName() + " does not exist.");
            return false;
        }
    }

}
