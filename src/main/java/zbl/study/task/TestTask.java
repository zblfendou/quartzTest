package zbl.study.task;

import org.springframework.context.ApplicationContext;
import zbl.study.model.TimedTask;

import java.time.LocalDateTime;

public class TestTask extends TimedTask {
    private int port;

    public TestTask(int port, LocalDateTime startTime) {
        super(startTime);
        this.port = port;
    }

    public TestTask() {
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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
        logger.info("remotePort:{} ,test task is executed !!! test task successfully !!!", getPort());
    }
}
