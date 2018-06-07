package zbl.study.task;

import org.springframework.context.ApplicationContext;
import zbl.study.model.TimedTask;

import java.time.LocalDateTime;

public class TestTask extends TimedTask {

    public TestTask(LocalDateTime startTime) {
        super(startTime);
    }

    public TestTask() {
    }

    @Override
    public String getJobName() {
        return "TESTTASKJOBNAME";
    }

    @Override
    public String getJobGroup() {
        return "TESTTASKJOBGROUP";
    }

    @Override
    public void executeTask(ApplicationContext context) {
        logger.info("test task is executed !!! test task successfully !!!");
    }
}
