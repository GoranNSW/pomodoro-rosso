package rs.goran.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import rs.goran.model.Team;
import rs.goran.model.User;

public class UserNotifier {

    private static final Logger logger = Logger.getLogger(UserNotifier.class);

    public static final String STATUS_STARTED = "started pomodoro";
    public static final String STATUS_PAUSED = "paused pomodoro";
    public static final String STATUS_RESET = "reset pomodoro";
    public static final String STATUS_SHORT_BREAK = "is on short break";
    public static final String STATUS_LONG_BREAK = "is on long break";
    public static final String STATUS_INACTIVE = "is inactive";
    public static final String TIME_POMODORO = "in pomodoro";
    public static final String TIME_PAUSE = "on pause";

    public UserNotifier() {

    }

    private Set<User> getUsers(User user) {
        Set<User> users = new HashSet<>();
        for (Team team : user.getTeams()) {
            for (User userFromTeam : team.getUsers()) {
                if (!users.contains(userFromTeam)) {
                    users.add(userFromTeam);
                }
            }
        }
        return users;
    }

    public void notifyUserStatus(User user, String status) {
        for (User notifyUser : getUsers(user)) {
            if (!user.getEmail().equals(notifyUser.getEmail())) {
                logger.info("Hey " + notifyUser.getName() + ", user " + user.getName() + " " + status + ".");
            }
        }
    }

    public void reportTime(User user, long time, String timeForEvent) {
        for (User notifyUser : getUsers(user)) {
            if (!user.getEmail().equals(notifyUser.getEmail())) {
                long sec = time % 60;
                long min = time / 60;
                String timeLeft = min + ":" + String.format("%02d", sec);
                logger.info("Hey " + notifyUser.getName() + ", user " + user.getName() + " has " + timeLeft + " left "
                        + timeForEvent + ".");
            }
        }
    }

}
