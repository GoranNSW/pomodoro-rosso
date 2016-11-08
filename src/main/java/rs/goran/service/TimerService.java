package rs.goran.service;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import rs.goran.model.Pomodoro;
import rs.goran.model.User;

public class TimerService {

    private static final Logger logger = Logger.getLogger(TimerService.class);

    Scheduler scheduler;

    public void startPomodoroCounter(Pomodoro pomodoro, long time) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobKey jobKey = new JobKey(pomodoro.getTaskName(), pomodoro.getUser().getEmail());
            if (!scheduler.checkExists(jobKey)) {
                JobDetail job = JobBuilder.newJob(TimeCounter.class)
                        .withIdentity(pomodoro.getTaskName(), pomodoro.getUser().getEmail()).build();
                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(pomodoro.getTaskName(), pomodoro.getUser().getEmail())
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(User.UPDATE_INTERVAL)
                                .repeatForever().withMisfireHandlingInstructionNextWithExistingCount())
                        .build();
                job.getJobDataMap().put(TimeCounter.TIME, User.POMODORO_TIME - time);
                job.getJobDataMap().put(TimeCounter.ENTITY, pomodoro);
                scheduler.start();
                scheduler.scheduleJob(job, trigger);
            } else {
                scheduler.resumeJob(jobKey);
            }
        } catch (SchedulerException e) {
            logger.error("Scheduler start pomodoro counter exception.");
        }
    }

    public void pausePomodoroCounter(Pomodoro pomodoro) {
        try {
            JobKey jobKey = new JobKey(pomodoro.getTaskName(), pomodoro.getUser().getEmail());
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("Scheduler pause pomodoro counter exception.");
        }
    }

    public void startPauseCounter(User user, long time) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobKey jobKey = new JobKey(user.getName(), user.getEmail());
            if (!scheduler.checkExists(jobKey)) {
                JobDetail job = JobBuilder.newJob(TimeCounter.class).withIdentity(user.getName(), user.getEmail())
                        .build();
                Trigger trigger = TriggerBuilder.newTrigger().withIdentity(user.getName(), user.getEmail())
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(User.UPDATE_INTERVAL)
                                .repeatForever().withMisfireHandlingInstructionNextWithExistingCount())
                        .build();
                job.getJobDataMap().put(TimeCounter.TIME, time);
                job.getJobDataMap().put(TimeCounter.ENTITY, user);
                scheduler.start();
                scheduler.scheduleJob(job, trigger);
            } else {
                scheduler.resumeJob(jobKey);
            }
        } catch (SchedulerException e) {
            logger.error("Scheduler start pause counter exception.");
        }
    }

    public void stopPauseCounter(User user) {
        try {
            JobKey jobKey = new JobKey(user.getName(), user.getEmail());
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            if (scheduler.getJobDetail(jobKey) != null) {
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            logger.error("Scheduler stop pause counter exception.");
        }
    }
}
