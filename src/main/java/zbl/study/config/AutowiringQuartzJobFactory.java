package zbl.study.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.inject.Named;

/**
 * 将job注入到spring上下文
 */
//目的 :使用Spring整合quartz实现动态任务时，
// 想在job定时任务中使用某个service时，直接通过加注解@Component、@Autowired是不能注入的，获取的对象为Null。
@Named
public class AutowiringQuartzJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        capableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object job = super.createJobInstance(bundle);
        capableBeanFactory.autowireBean(job);
        return job;
    }
}
