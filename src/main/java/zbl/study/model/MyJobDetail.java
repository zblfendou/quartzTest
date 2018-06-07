package zbl.study.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zbl.study.config.TaskExcuter;

import javax.inject.Inject;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

public class MyJobDetail implements Job {
    @Inject
    private ObjectMapper objectMapper;
    @Inject
    private TaskExcuter taskExcuter;
    private String timedTask, classname, cronTask;
    private static final Logger logger = LoggerFactory.getLogger(MyJobDetail.class);

    public String getTimedTask() {
        return timedTask;
    }

    public void setTimedTask(String timedTask) {
        this.timedTask = timedTask;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getCronTask() {
        return cronTask;
    }

    public void setCronTask(String cronTask) {
        this.cronTask = cronTask;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            if (hasText(timedTask)) {
                taskExcuter.executeTask((TimedTask) objectMapper.readValue(timedTask, Class.forName(classname)));
            } else if (hasText(cronTask)) {
                taskExcuter.executeTask((TimedTask) objectMapper.readValue(timedTask, Class.forName(classname)));
            } else logger.error("执行任务出错了.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
