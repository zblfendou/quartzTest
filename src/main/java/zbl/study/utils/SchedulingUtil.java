package zbl.study.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import zbl.study.model.CronTask;
import zbl.study.model.MyJobDetail;
import zbl.study.model.Task;
import zbl.study.model.TimedTask;

import javax.inject.Inject;
import javax.inject.Named;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Named
public class SchedulingUtil {
    private static final Logger logger = LoggerFactory.getLogger(SchedulingUtil.class);
    @Inject
    private ObjectMapper objectMapper;
    @Inject
    private Scheduler scheduler;

    @Transactional
    public void addTimeTaskSchedule(TimedTask job) {
        try {
            TriggerKey triggerKey = new TriggerKey(job.getJobName(), job.getJobGroup());
            Trigger trigger = scheduler.getTrigger(triggerKey);
            JobDetail jobDetail = JobBuilder.newJob(MyJobDetail.class).withIdentity(job.getJobName(), job.getJobGroup()).build();
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            jobDataMap.put("classname", job.getClass().getTypeName());
            jobDataMap.put("timedTask", getTaskJson(job));

            if (trigger == null) {
                trigger = TriggerBuilder.newTrigger().startAt(job.getStartTime()).withIdentity(triggerKey).build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                scheduler.unscheduleJob(triggerKey);
                this.addTimeTaskSchedule(job);
            }

        } catch (SchedulerException | JsonProcessingException e) {
            logger.error("安排任务失败", e);
        }
    }

    private String getTaskJson(Task job) throws JsonProcessingException {
        return objectMapper.writeValueAsString(job);
    }

    @Transactional
    public void cancelTimedTaskSchedule(TimedTask job) {
        TriggerKey triggerKey = new TriggerKey(job.getJobName(), job.getJobName());
        try {
            scheduler.unscheduleJob(triggerKey);
        } catch (SchedulerException e) {
            logger.error("取消任务失败,", e);
        }
    }

    @Transactional
    public void addCronTaskSchedule(CronTask job) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

            Trigger trigger = scheduler.getTrigger(triggerKey);

            //不存在，创建一个
            if (null == trigger) {
                JobDetail jobDetail = JobBuilder.newJob(MyJobDetail.class)
                        .withIdentity(job.getJobName(), job.getJobGroup()).build();
                JobDataMap jobDataMap = jobDetail.getJobDataMap();
                jobDataMap.put("classname", job.getClass().getTypeName());
                jobDataMap.put("cronTask", getTaskJson(job));
                //按 定时时间 构建trigger

                trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(CronScheduleBuilder.cronSchedule(job.getCondExpession())).build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                scheduler.unscheduleJob(triggerKey);
                addCronTaskSchedule(job);
            }
        } catch (SchedulerException | JsonProcessingException e) {
            logger.debug("安排任务失败", e);
        }
    }
}
