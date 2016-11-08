package rs.goran.service;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.SchedulerException;

import rs.goran.model.Pomodoro;
import rs.goran.model.User;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TimeCounter implements Job {

    private static final Logger logger = Logger.getLogger(TimeCounter.class);

    public static final String TIME = "time";
    public static final String ENTITY = "entity";
    private static final String TYPE_POMODORO = "pomodoro";
    private static final String TYPE_USER = "user";
    
    private Object type;
    private String selectedType;
    private JobDataMap dataMap;
    private Pomodoro pomodoro;
    private User user;
    private long time;
    private UserNotifier pomodoroNotifier = new UserNotifier();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        if (dataMap == null) {
            dataMap = context.getJobDetail().getJobDataMap();
            time = dataMap.getLong(TIME);
            type = dataMap.get(ENTITY);
            
            if (type instanceof Pomodoro) {
                pomodoro = (Pomodoro) type;
                selectedType = TYPE_POMODORO;
            } else if (type instanceof User) {
                user = (User) type;
                selectedType = TYPE_USER;
            }
        }

        if (time == 0) {
            deleteJob(context);
            finishTask();
        } else {
            reportTime();
            time -= User.UPDATE_INTERVAL;
            dataMap.put(TIME, time);
        }
    }

    private void deleteJob(JobExecutionContext context) {
        try {
            context.getScheduler().deleteJob(context.getJobDetail().getKey());
        } catch (SchedulerException e) {
            logger.error("Scheduler has performed exception during pomodoro time counting.");
        }
    }
    
    private void finishTask() {
        if (selectedType == TYPE_POMODORO) {
            pomodoro.getUser().finishPomodoro(pomodoro);
        } else if (selectedType == TYPE_USER) {
            user.finishPause(user);
        }
    }
    
    private void reportTime() {
        if (selectedType == TYPE_POMODORO) {
            pomodoroNotifier.reportTime(pomodoro.getUser(), time, UserNotifier.TIME_POMODORO);
        } else if (selectedType == TYPE_USER) {
            pomodoroNotifier.reportTime(user, time, UserNotifier.TIME_PAUSE);
        }
    }
}
