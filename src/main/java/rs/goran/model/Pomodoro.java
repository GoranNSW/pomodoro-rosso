
package rs.goran.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name = "pomodoro")
public class Pomodoro {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "date_time_started")
    private long dateTimeStarted;

    @Column(name = "date_time_paused")
    private long dateTimePaused;

    @Column(name = "finished")
    private int finished;

    @Column(name = "user")
    private String user;

    @Column(name = "team")
    private String team;

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
        if (this.finished == 1)
            return true;
        else
            return false;
    }

    public void setFinished(boolean finished) {
        if (finished)
            this.finished = 1;
        else
            this.finished = 0;
    }

}
