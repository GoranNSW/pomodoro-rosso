
package rs.goran.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.Session;

import rs.goran.service.HibernateUtil;

@Entity
public class User {

    @Id
    private String email;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_pomodoro")
    private Set<Pomodoro> pomodoroList = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "userList")
    private Set<Team> teamList = new HashSet<>();

    public User() {

    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Pomodoro> getPomodoroList() {
        return pomodoroList;
    }

    public void setPomodoroList(Set<Pomodoro> pomodoroList) {
        this.pomodoroList = pomodoroList;
    }

    public Set<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(Set<Team> teamList) {
        this.teamList = teamList;
    }

    public boolean addTeam(Team team) {
        if (!this.getTeamList().contains(team)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            this.getTeamList().add(team);
            session.saveOrUpdate(this);
            session.getTransaction().commit();
            session.close();
            System.out.println("Team " + team.getName() + " added.");
            return true;
        } else {
            System.out.println("Team " + team.getName() + " already exists in database.");
            return false;
        }
    }

    public boolean removeTeam(Team team) {
        if (this.getTeamList().contains(team)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            this.getTeamList().remove(team);
            session.saveOrUpdate(this);
            session.getTransaction().commit();
            session.close();
            System.out.println("Team " + team.getName() + " deleted.");
            return true;
        } else {
            System.out.println("Team " + team.getName() + " does not exist.");
            return false;
        }
    }

    public boolean addPomodoro(Pomodoro pomodoro) {
        if (!this.getPomodoroList().contains(pomodoro)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            this.getPomodoroList().add(pomodoro);
            pomodoro.setUser(this);
            session.saveOrUpdate(this);
            session.getTransaction().commit();
            session.close();
            System.out.println("Pomodoro " + pomodoro.getTaskName() + " added.");
            return true;
        } else {
            System.out.println("Pomodoro " + pomodoro.getTaskName() + " already exists in database.");
            return false;
        }
    }

    public boolean removePomodoro(Pomodoro pomodoro) {
        if (this.getPomodoroList().contains(pomodoro)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            this.getPomodoroList().remove(pomodoro);
            session.saveOrUpdate(this);
            session.getTransaction().commit();
            session.close();
            System.out.println("Pomodoro " + pomodoro.getTaskName() + " deleted.");
            return true;
        } else {
            System.out.println("Pomodoro " + pomodoro.getTaskName() + " does not exist.");
            return false;
        }
    }

}
