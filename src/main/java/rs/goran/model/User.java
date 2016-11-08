
package rs.goran.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import rs.goran.service.HibernateUtil;
import rs.goran.service.UserNotifier;
import rs.goran.service.TimerService;

@Entity
public class User {

    private static final Logger logger = Logger.getLogger(User.class);

    public static final long POMODORO_TIME = 1500; // 1500seconds = 25minutes
    public static final long LONG_PAUSE_TIME = 900; // 900seconds = 15minutes
    public static final long SHORT_PAUSE_TIME = 300; // 300seconds = 5minutes
    public static final int UPDATE_INTERVAL = 1; // update interval of
                                                 // TimerService

    @Id
    private String id;

    private String email;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_pomodoro")
    private Set<Pomodoro> pomodoros = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Team> teams = new HashSet<>();

    @Transient
    private boolean inPomodoro = false;

    @Transient
    UserNotifier pomodoroNotifier;

    public User() {

    }

    public User(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.pomodoroNotifier = new UserNotifier();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Set<Pomodoro> getPomodoros() {
        return pomodoros;
    }

    public void setPomodoros(Set<Pomodoro> pomodoros) {
        this.pomodoros = pomodoros;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public boolean addTeam(Team team) {
        if (!this.getTeams().contains(team)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            this.getTeams().add(team);
            session.saveOrUpdate(this);
            team.addUser(this);
            session.saveOrUpdate(team);
            session.getTransaction().commit();
            session.close();
            logger.info("Team " + team.getName() + " added.");
            return true;
        } else {
            logger.warn("Team " + team.getName() + " already exists in database.");
            return false;
        }
    }

    public boolean removeTeam(Team team) {
        if (this.getTeams().contains(team)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            this.getTeams().remove(team);
            session.saveOrUpdate(this);
            team.removeUser(this);
            session.saveOrUpdate(team);
            session.getTransaction().commit();
            session.close();
            logger.info("Team " + team.getName() + " deleted.");
            return true;
        } else {
            logger.warn("Team " + team.getName() + " does not exist.");
            return false;
        }
    }

    public boolean addPomodoro(Pomodoro pomodoro) {
        if (!this.pomodoros.contains(pomodoro)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            this.pomodoros.add(pomodoro);
            pomodoro.setUser(this);
            session.saveOrUpdate(this);
            session.getTransaction().commit();
            session.close();
            logger.info("Pomodoro " + pomodoro.getTaskName() + " added.");
            return true;
        } else {
            logger.warn("Pomodoro " + pomodoro.getTaskName() + " already exists in database.");
            return false;
        }
    }

    public boolean removePomodoro(Pomodoro pomodoro) {
        if (this.pomodoros.contains(pomodoro) && !this.inPomodoro) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            this.pomodoros.remove(pomodoro);
            session.saveOrUpdate(this);
            session.getTransaction().commit();
            session.close();
            logger.info("Pomodoro " + pomodoro.getTaskName() + " deleted.");
            return true;
        } else {
            logger.warn("Pomodoro " + pomodoro.getTaskName() + " does not exist or is locked.");
            return false;
        }
    }

    public boolean startPomodoro(Pomodoro pomodoro) {
        long timeDifference;

        if (this.pomodoros.contains(pomodoro)) {
            if (!pomodoro.getIsFinished() && !this.inPomodoro) {
                TimerService timerService = new TimerService();
                timerService.stopPauseCounter(this);
                if (pomodoro.getDateTimeStarted() == null) {
                    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                    session.beginTransaction();
                    pomodoro.setDateTimeStarted(new Date());
                    session.saveOrUpdate(pomodoro);
                    session.getTransaction().commit();
                    session.close();
                    timeDifference = 0;
                    timerService.startPomodoroCounter(pomodoro, timeDifference);
                } else {
                    timeDifference = getTimeDifference(pomodoro.getDateTimeStarted(), pomodoro.getDateTimePaused(),
                            TimeUnit.SECONDS);
                    timerService.startPomodoroCounter(pomodoro, timeDifference);
                }
                logger.info("Pomodoro " + pomodoro.getTaskName() + " started.");
                this.inPomodoro = true;
                this.pomodoroNotifier.notifyUserStatus(this, UserNotifier.STATUS_STARTED);
                return true;
            } else {
                logger.warn("Pomodoro " + pomodoro.getTaskName() + " can NOT be started.");
                return false;
            }
        } else {
            logger.warn("Pomodoro " + pomodoro.getTaskName() + " does not exist.");
            return false;
        }
    }

    public boolean pausePomodoro(Pomodoro pomodoro) {
        if (this.pomodoros.contains(pomodoro) && pomodoro.getDateTimeStarted() != null && this.inPomodoro) {
            if (!pomodoro.getIsFinished()) {
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                pomodoro.setDateTimePaused(new Date());
                session.saveOrUpdate(pomodoro);
                session.getTransaction().commit();
                session.close();
                new TimerService().pausePomodoroCounter(pomodoro);
                logger.info("Pomodoro " + pomodoro.getTaskName() + " is paused.");
                this.inPomodoro = false;
                this.pomodoroNotifier.notifyUserStatus(this, UserNotifier.STATUS_PAUSED);
                return true;
            }
            logger.warn("Pomodoro " + pomodoro.getTaskName() + " is already finished.");
            return false;
        } else {
            logger.warn("Pomodoro " + pomodoro.getTaskName() + " does not exist or it is not started yet.");
            return false;
        }
    }

    public boolean resetPomodoro(Pomodoro pomodoro) {
        if (this.pomodoros.contains(pomodoro) && !this.inPomodoro) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            pomodoro.setDateTimeStarted(null);
            pomodoro.setDateTimePaused(null);
            pomodoro.setIsFinished(false);
            session.saveOrUpdate(pomodoro);
            session.getTransaction().commit();
            session.close();
            this.inPomodoro = false;
            logger.info("Pomodoro " + pomodoro.getTaskName() + " is reset.");
            this.pomodoroNotifier.notifyUserStatus(this, UserNotifier.STATUS_RESET);
            return true;
        } else {
            logger.warn("Pomodoro " + pomodoro.getTaskName() + " does not exist or can NOT be reset.");
            return false;
        }
    }

    public boolean finishPomodoro(Pomodoro pomodoro) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        pomodoro.setIsFinished(true);
        session.saveOrUpdate(pomodoro);
        session.getTransaction().commit();
        session.close();
        int finishedCount = 0;
        for (Pomodoro isFinishedPomodoro : pomodoro.getUser().getPomodoros()) {
            if (isFinishedPomodoro.getIsFinished()) {
                finishedCount++;
            }
        }
        TimerService timerService = new TimerService();
        if ((finishedCount % 4) == 0) {
            timerService.startPauseCounter(pomodoro.getUser(), LONG_PAUSE_TIME);
            this.pomodoroNotifier.notifyUserStatus(this, UserNotifier.STATUS_LONG_BREAK);
        } else {
            timerService.startPauseCounter(pomodoro.getUser(), SHORT_PAUSE_TIME);
            this.pomodoroNotifier.notifyUserStatus(this, UserNotifier.STATUS_SHORT_BREAK);
        }
        this.inPomodoro = false;
        return true;
    }

    public boolean finishPause(User user) {
        this.pomodoroNotifier.notifyUserStatus(this, UserNotifier.STATUS_INACTIVE);
        return true;
    }

    private long getTimeDifference(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.SECONDS);
    }

}
