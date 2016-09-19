package rs.goran.service;

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

    Scheduler scheduler;

    public boolean startPomodoroCounter(Pomodoro pomodoro, long time) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobKey jobKey = new JobKey(pomodoro.getTaskName(), pomodoro.getUser().getEmail());
            if (!scheduler.checkExists(jobKey)) {
                JobDetail job = JobBuilder.newJob(TimeCounterPomodoro.class)
                        .withIdentity(pomodoro.getTaskName(), pomodoro.getUser().getEmail()).build();
                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(pomodoro.getTaskName(), pomodoro.getUser().getEmail())
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(User.UPDATE_INTERVAL)
                                .repeatForever().withMisfireHandlingInstructionNextWithExistingCount())
                        .build();
                job.getJobDataMap().put(TimeCounterPomodoro.TIME, User.POMODORO_TIME - time);
                job.getJobDataMap().put(TimeCounterPomodoro.POMODORO, pomodoro);
                scheduler.start();
                scheduler.scheduleJob(job, trigger);
            } else {
                scheduler.resumeJob(jobKey);
            }
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }
 
    public boolean pausePomodoroCounter(Pomodoro pomodoro) {
        try {
            JobKey jobKey = new JobKey(pomodoro.getTaskName(), pomodoro.getUser().getEmail());
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean startPauseCounter(User user, long time) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobKey jobKey = new JobKey(user.getName(), user.getEmail());
            if (!scheduler.checkExists(jobKey)) {
                JobDetail job = JobBuilder.newJob(TimeCounterPause.class)
                        .withIdentity(user.getName(), user.getEmail()).build();
                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(user.getName(), user.getEmail())
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(User.UPDATE_INTERVAL)
                                .repeatForever().withMisfireHandlingInstructionNextWithExistingCount())
                        .build();
                	job.getJobDataMap().put(TimeCounterPause.TIME, time);    
                job.getJobDataMap().put(TimeCounterPause.USER, user);
                scheduler.start();
                scheduler.scheduleJob(job, trigger);
            } else {
                scheduler.resumeJob(jobKey);
            }
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }
 
    public boolean stopPauseCounter(User user) {
        try {
            JobKey jobKey = new JobKey(user.getName(), user.getEmail());
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            if (scheduler.getJobDetail(jobKey) != null) {
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return true;
    }
}
