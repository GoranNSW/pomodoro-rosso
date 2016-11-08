
package rs.goran.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.log4j.Logger;

@Entity
public class Pomodoro {

    private static final Logger logger = Logger.getLogger(Pomodoro.class);

    @Id
    private String taskName;

    private Date dateTimeStarted;

    private Date dateTimePaused;

    private boolean finished;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Team team;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;

    public Pomodoro() {

    }

    public Pomodoro(String taskName, Team team) {
        super();
        this.taskName = taskName;
        this.team = team;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getDateTimeStarted() {
        return dateTimeStarted;
    }

    public void setDateTimeStarted(Date dateTimeStarted) {
        this.dateTimeStarted = dateTimeStarted;
    }

    public Date getDateTimePaused() {
        return dateTimePaused;
    }

    public void setDateTimePaused(Date dateTimePaused) {
        this.dateTimePaused = dateTimePaused;
    }

    public boolean getIsFinished() {
        return finished;
    }

    public void setIsFinished(boolean finished) {
        this.finished = finished;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
