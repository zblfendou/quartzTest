package zbl.study.model;

import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public abstract class TimedTask extends Task {
    private Date startTime;


    public TimedTask(LocalDateTime startTime) {
        this();
        this.startTime = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public abstract String getJobName();

    public abstract String getJobGroup();

    public TimedTask() {
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public void executeTask(ApplicationContext context) {
        logger.error("unknown model type:{}", TimedTask.class.getName());
    }
}
