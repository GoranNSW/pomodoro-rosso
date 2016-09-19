
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
import rs.goran.service.PomodoroNotifier;
import rs.goran.service.TimerService;

@Entity
public class User {

    @Id
    private String email;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_pomodoro")
    private Set<Pomodoro> pomodoroList = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "userList", fetch = FetchType.EAGER)
    private Set<Team> teamList = new HashSet<>();
    
    @Transient
    private boolean inPomodoro = false;

    public static final long POMODORO_TIME = 1500; // 1500seconds = 25minutes
    public static final long LONG_PAUSE_TIME = 900; // 900seconds = 15minuutes
    public static final long SHORT_PAUSE_TIME = 300; // 300seconds = 5minuutes
    public static final int UPDATE_INTERVAL = 1; // update interval of
                                                 // TimerService

    final static Logger logger = Logger.getLogger(User.class);

    @Transient
    PomodoroNotifier pomodoroNotifier;

    public User() {

    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
        this.pomodoroNotifier = new PomodoroNotifier();

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
        if (this.getTeamList().contains(team)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            this.getTeamList().remove(team);
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
        if (!this.getPomodoroList().contains(pomodoro)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            this.getPomodoroList().add(pomodoro);
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
        if (this.getPomodoroList().contains(pomodoro) && !this.inPomodoro) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            this.getPomodoroList().remove(pomodoro);
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

        if (this.getPomodoroList().contains(pomodoro)) {
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
                this.pomodoroNotifier.notifyUserStartedPomodoro(this);
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
        if (this.getPomodoroList().contains(pomodoro) && pomodoro.getDateTimeStarted() != null && this.inPomodoro) {
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
                this.pomodoroNotifier.notifyUserPausedPomodoro(this);
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
        if (this.getPomodoroList().contains(pomodoro) && !this.inPomodoro) {
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
            this.pomodoroNotifier.notifyUserResetPomodoro(this);
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
        for (Pomodoro isFinishedPomodoro : pomodoro.getUser().getPomodoroList()) {
            if (isFinishedPomodoro.getIsFinished()) {
                finishedCount++;
            }
        }
        TimerService timerService = new TimerService();
        if ((finishedCount % 4) == 0) {
        	timerService.startPauseCounter(pomodoro.getUser(), LONG_PAUSE_TIME);
            this.pomodoroNotifier.notifyUserLongBreak(this);
        } else {
        	timerService.startPauseCounter(pomodoro.getUser(), SHORT_PAUSE_TIME);        	
            this.pomodoroNotifier.notifyUserShortBreak(this);
        }
        this.inPomodoro = false;
        return true;
    }
    
    public boolean finishPause(User user) {
    	this.pomodoroNotifier.notifyUserInactive(this);
        return true;
    }

    private long getTimeDifference(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.SECONDS);
    }

}
