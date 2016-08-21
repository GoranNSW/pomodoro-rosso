package rs.goran.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pomodoro")
public class Pomodoro {

    @Id
    @Column
    private String user;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "date_time_started")
    private long dateTimeStarted;

    @Column(name = "date_time_paused")
    private long dateTimePaused;

    @Column(name = "finished")
    private boolean finished;

    public Pomodoro(String taskName) {
        this.taskName = taskName;
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

    public void startPomodoro() {
        Date date = new Date();
        this.dateTimeStarted = date.getTime();
        System.out.println("Pomodoro started at " + date.getTime());
    }

    public long getDateTimePaused() {
        return dateTimePaused;
    }

    public void pausePomodoro() {
        Date date = new Date();
        this.dateTimePaused = date.getTime();
        System.out.println("Pomodoro paused at " + date.getTime());
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

}
