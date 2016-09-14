package rs.goran.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import rs.goran.model.Team;
import rs.goran.model.User;

public class PomodoroNotifier {

    final static Logger logger = Logger.getLogger(PomodoroNotifier.class);

    public PomodoroNotifier() {

    }

    private Set<User> getUsersList(User user) {
        Set<Team> teams = new HashSet<>();
        Set<User> users = new HashSet<>();
        teams = user.getTeamList();
        for (Team team : teams) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Team teamFromDB = session.get(Team.class, team.getName());
            session.close();
            users = teamFromDB.getUserList();
        }
        return users;
    }

    public void notifyUserStartedPomodoro(User user) {
        for (User notifyUser : getUsersList(user)) {
            if (!user.getEmail().equals(notifyUser.getEmail())) {
                logger.info("Hey " + notifyUser.getName() + ", user " + user.getName() + " started pomodoro.");
            }
        }
    }

    public void notifyUserPausedPomodoro(User user) {
        for (User notifyUser : getUsersList(user)) {
            if (!user.getEmail().equals(notifyUser.getEmail())) {
                logger.info("Hey " + notifyUser.getName() + ", user " + user.getName() + " paused pomodoro.");
            }
        }
    }

    public void notifyUserResetPomodoro(User user) {
        for (User notifyUser : getUsersList(user)) {
            if (!user.getEmail().equals(notifyUser.getEmail())) {
                logger.info("Hey " + notifyUser.getName() + ", user " + user.getName() + " reset pomodoro.");
            }
        }
    }

    public void notifyUserShortBreak(User user) {
        for (User notifyUser : getUsersList(user)) {
            if (!user.getEmail().equals(notifyUser.getEmail())) {
                logger.info("Hey " + notifyUser.getName() + ", user " + user.getName() + " is on short break.");
            }
        }
    }

    public void notifyUserLongBreak(User user) {
        for (User notifyUser : getUsersList(user)) {
            if (!user.getEmail().equals(notifyUser.getEmail())) {
                logger.info("Hey " + notifyUser.getName() + ", user " + user.getName() + " is on long break.");
            }
        }
    }

}
