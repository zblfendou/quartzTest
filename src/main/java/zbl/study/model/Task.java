package zbl.study.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;

import java.io.Serializable;

public abstract class Task implements Serializable {
    protected final static Logger logger = LoggerFactory.getLogger(TaskExecutor.class);

    public abstract void executeTask(ApplicationContext context);

    protected <T> T getService(ApplicationContext context, Class<T> tClass) {
        return context.getBean(tClass);
    }
}
