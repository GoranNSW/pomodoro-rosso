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

    public boolean startTimerService(Pomodoro pomodoro, long timeDifference) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobKey jobKey = new JobKey(pomodoro.getTaskName(), pomodoro.getUser().getEmail());
            if (!scheduler.checkExists(jobKey)) {
                JobDetail job = JobBuilder.newJob(PomodoroTimeCounter.class)
                        .withIdentity(pomodoro.getTaskName(), pomodoro.getUser().getEmail()).build();
                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(pomodoro.getTaskName(), pomodoro.getUser().getEmail())
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(User.UPDATE_INTERVAL)
                                .repeatForever().withMisfireHandlingInstructionNextWithExistingCount())
                        .build();
                job.getJobDataMap().put(PomodoroTimeCounter.TIME, User.POMODORO_TIME - timeDifference);
                job.getJobDataMap().put(PomodoroTimeCounter.POMODORO, pomodoro);
                job.getJobDataMap().put("pause_job", false);
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

    public boolean pauseTimerService(Pomodoro pomodoro) {
        try {
            JobKey jobKey = new JobKey(pomodoro.getTaskName(), pomodoro.getUser().getEmail());
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return true;
    }

}
