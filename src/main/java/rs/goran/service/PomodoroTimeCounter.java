package rs.goran.service;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.SchedulerException;

import rs.goran.model.Pomodoro;
import rs.goran.model.User;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class PomodoroTimeCounter implements Job {

    public static final String TIME = "time";
    public static final String POMODORO = "pomodoro";

    final static Logger logger = Logger.getLogger(PomodoroTimeCounter.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        long time = dataMap.getLong(TIME);

        if (time == 0) {
            JobKey jobKey = context.getJobDetail().getKey();
            try {
                context.getScheduler().deleteJob(jobKey);
                Pomodoro pomodoro = (Pomodoro) dataMap.get(POMODORO);
                pomodoro.getUser().finishPomodoro(pomodoro);
            } catch (SchedulerException e) {
                logger.error("Scheduler has performed exception during pomodoro time counting.");
                e.printStackTrace();
            }
        } else {
            time -= User.UPDATE_INTERVAL;
            dataMap.put(TIME, time);
        }
    }
}
