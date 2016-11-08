package rs.goran.service;

import org.apache.log4j.Logger;

import rs.goran.model.Pomodoro;
import rs.goran.model.Team;
import rs.goran.model.User;

public class PomodoroService {
    
    private static final Logger logger = Logger.getLogger(PomodoroService.class);

    public void addPomodoro(Pomodoro pomodoro) {
        Pomodoro newPomodoro = new Pomodoro(pomodoro.getTaskName(), pomodoro.getTeam());
        logger.info("Pomodoro " + pomodoro.getTaskName() + " added.");
    }
    
    public void addUserToPomodoro(User user, Pomodoro pomodoro) {
        user.addPomodoro(pomodoro);
        logger.info("User " + user.getName() + " added to pomodoro " + pomodoro.getTaskName());
    }

    public void startPomodoro(User user, Pomodoro pomodoro) {
        user.startPomodoro(pomodoro);
        logger.info("User " + user.getName() + " started pomodoro " + pomodoro.getTaskName());
    }
    
    public void pausePomodoro(User user, Pomodoro pomodoro) {
        user.pausePomodoro(pomodoro);
        logger.info("User " + user.getName() + " started pomodoro " + pomodoro.getTaskName());
    }
}
