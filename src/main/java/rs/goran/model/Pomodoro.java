
package rs.goran.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Pomodoro {

    @Id
    private String taskName;

    private long dateTimeStarted;

    private long dateTimePaused;

    private boolean finished;

    @ManyToOne
    private Team team;

    @ManyToOne
    private User user;

    public Pomodoro() {

    }

    public Pomodoro(String taskName, long dateTimeStarted, long dateTimePaused, boolean finished, Team team) {
        super();
        this.taskName = taskName;
        this.dateTimeStarted = dateTimeStarted;
        this.dateTimePaused = dateTimePaused;
        this.finished = finished;
        this.team = team;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public long getDateTimeStarted() {
        return dateTimeStarted;
    }

    public void setDateTimeStarted(long dateTimeStarted) {
        this.dateTimeStarted = dateTimeStarted;
    }

    public long getDateTimePaused() {
        return dateTimePaused;
    }

    public void setDateTimePaused(long dateTimePaused) {
        this.dateTimePaused = dateTimePaused;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
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

    // public void startPomodoro() {
    // Date date = new Date();
    // this.dateTimeStarted = date.getTime();
    // System.out.println("Pomodoro started at " + date.getTime());

}
