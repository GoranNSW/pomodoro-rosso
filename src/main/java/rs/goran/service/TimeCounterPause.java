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

import rs.goran.model.User;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TimeCounterPause implements Job {

    public static final String TIME = "time";
    public static final String USER = "user";
    public long time;

    private JobDataMap dataMap;
    private User user;
    private PomodoroNotifier pomodoroNotifier = new PomodoroNotifier();
    
    final static Logger logger = Logger.getLogger(TimeCounterPause.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        if (user == null) {
            dataMap = context.getJobDetail().getJobDataMap();
        	user = (User) dataMap.get(USER);
            time = dataMap.getLong(TIME);
        }

        if (time == 0) {
            try {
                JobKey jobKey = context.getJobDetail().getKey();
                context.getScheduler().deleteJob(jobKey);                
                user.finishPause(user);
            } catch (SchedulerException e) {
                logger.error("Scheduler has performed exception during pause time counting.");
                e.printStackTrace();
            }
        } else {
        	pomodoroNotifier.reportPauseTime(user, time);
            time -= User.UPDATE_INTERVAL;
            dataMap.put(TIME, time);
        }
    }
}
