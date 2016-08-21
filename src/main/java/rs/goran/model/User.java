package rs.goran.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Pomodoro> pomodoros;

    @OneToMany(targetEntity = Team.class, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Team> teams;

    public User() {

    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
        this.pomodoros = new ArrayList<>();
        this.teams = new ArrayList<>();
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

    public void addPomodoro(String taskName) {
        // TODO check duplicate name from DB
        try {
            Pomodoro pomodoro = new Pomodoro(taskName);
            this.pomodoros.add(pomodoro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Pomodoro> getPomodoros() {
        return pomodoros;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void addTeam(String teamName) {
        try {
            Team team = new Team(teamName);
            this.teams.add(team);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean deleteTeam(String teamName) {
        boolean isFound = false;
        for (Team team : this.teams) {
            if (team.getName().equals(teamName)) {
                isFound = this.teams.remove(team);
            }
        }
        return isFound; // TODO - if found show success, otherwise show not
                        // found
    }

}
