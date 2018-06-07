package zbl.study.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.task.TaskExecutor;
import zbl.study.model.Task;

import javax.inject.Named;

@Named("taskExcuter")
public class TaskExcuter implements ApplicationContextAware {
    private final static Logger logger = LoggerFactory.getLogger(TaskExecutor.class);

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public void executeTask(Task task) {
        logger.debug("Executing model :{}", task.getClass().getName());
        task.executeTask(context);
    }

}
