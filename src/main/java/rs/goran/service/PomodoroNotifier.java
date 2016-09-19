package rs.goran.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import rs.goran.model.Team;
import rs.goran.model.User;

public class PomodoroNotifier {

    final static Logger logger = Logger.getLogger(PomodoroNotifier.class);

    public PomodoroNotifier() {

    }

    private Set<User> getUsersList(User user) {
        Set<User> users = new HashSet<>();
        for (Team team : user.getTeamList()) {
        	for (User userFromTeam : team.getUserList()){
        		if (!users.contains(userFromTeam)) {
        			users.add(userFromTeam);
        		}
        	}   
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
    
    public void reportPomodoroTime(User user, long time) {
        for (User notifyUser : getUsersList(user)) {
            if (!user.getEmail().equals(notifyUser.getEmail())) {
            	long sec = time % 60;
            	long min = time / 60;
            	String timeLeft = min + ":" + String.format("%02d", sec);
                logger.info("Hey " + notifyUser.getName() + ", user " + user.getName() + " has " +  timeLeft  + " left in pomodoro.");
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

    
    public void reportPauseTime(User user, long time) {
        for (User notifyUser : getUsersList(user)) {
            if (!user.getEmail().equals(notifyUser.getEmail())) {
            	long sec = time % 60;
            	long min = time / 60;
            	String timeLeft = min + ":" + String.format("%02d", sec);
                logger.info("Hey " + notifyUser.getName() + ", user " + user.getName() + " has " +  timeLeft  + " left on pause.");
            }
        }
    }
    
    public void notifyUserInactive(User user) {
        for (User notifyUser : getUsersList(user)) {
            if (!user.getEmail().equals(notifyUser.getEmail())) {
                logger.info("Hey " + notifyUser.getName() + ", user " + user.getName() + " is inactive.");
            }
        }
    }

}
