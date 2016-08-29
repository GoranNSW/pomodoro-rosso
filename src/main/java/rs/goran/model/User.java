
package rs.goran.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinTable(name = "pomodoro")
    private Collection<Pomodoro> pomodoroList = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "team_members")
    private Collection<Team> teamList = new ArrayList<>();

    public User() {

    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public int getUserId() {
        return id;
    }

    public void setUserId(int userId) {
        this.id = userId;
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

    public Collection<Pomodoro> getPomodoros() {
        return pomodoroList;
    }

    public void setPomodoros(Collection<Pomodoro> pomodoros) {
        this.pomodoroList = pomodoros;
    }

    public Collection<Team> getTeams() {
        return teamList;
    }

    public void setTeams(Collection<Team> teams) {
        this.teamList = teams;
    }

    // public void addPomodoro(String taskName) {
    // // TODO check duplicate name from DB
    //
    // try {
    // Pomodoro pomodoro = new Pomodoro(taskName);
    // this.pomodoroList.add(pomodoro);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    // public List<Pomodoro> getPomodoros() {
    // return pomodoros;
    // }
    //
    // public List<Team> getTeams() {
    // return teams;
    // }
    //
    // public void addTeam(String teamName) {
    // try {
    // Team team = new Team(teamName);
    // this.teams.add(team);
    // } catch (Exception e)
    //
    // {
    // e.printStackTrace();
    // }
    // }
    //
    // public boolean deleteTeam(String teamName) {
    // boolean isFound = false;
    // for (Team team : this.teams) {
    // if (team.getName().equals(teamName)) {
    // isFound = this.teams.remove(team);
    // }
    // }
    // return isFound; // TODO - if found show success, otherwise show not found
    // }

}
