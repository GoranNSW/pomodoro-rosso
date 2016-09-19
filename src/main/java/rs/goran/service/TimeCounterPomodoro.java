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
public class TimeCounterPomodoro implements Job {

    public static final String TIME = "time";
    public static final String POMODORO = "pomodoro";
    public long time;

    private JobDataMap dataMap;
    private Pomodoro pomodoro;
    private PomodoroNotifier pomodoroNotifier = new PomodoroNotifier();
    
    final static Logger logger = Logger.getLogger(TimeCounterPomodoro.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        if (pomodoro == null) {
            dataMap = context.getJobDetail().getJobDataMap();
        	pomodoro = (Pomodoro) dataMap.get(POMODORO);
            time = dataMap.getLong(TIME);
        }

        if (time == 0) {
            try {
                JobKey jobKey = context.getJobDetail().getKey();
                context.getScheduler().deleteJob(jobKey);                
                pomodoro.getUser().finishPomodoro(pomodoro);
            } catch (SchedulerException e) {
                logger.error("Scheduler has performed exception during pomodoro time counting.");
                e.printStackTrace();
            }
        } else {
        	pomodoroNotifier.reportPomodoroTime(pomodoro.getUser(), time);
            time -= User.UPDATE_INTERVAL;
            dataMap.put(TIME, time);
        }
    }
}
