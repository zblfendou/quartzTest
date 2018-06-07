package zbl.study.config;

import org.quartz.SchedulerFactory;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * 读取quartz配置,并将schedulerFactory实例放到spring上下文
 */
@Configuration
public class QuartzSchedulerConfig {
    @Inject
    private DataSource dataSource;
    @Inject
    private PlatformTransactionManager txManager;
    @Inject
    private JobFactory jobFactory;

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("/quartz.properties"));
        bean.afterPropertiesSet();
        return bean.getObject();
    }

    @Bean("schedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJobFactory(jobFactory);
        factoryBean.setQuartzProperties(quartzProperties());
        factoryBean.setTransactionManager(txManager);
        factoryBean.setOverwriteExistingJobs(true);
        return factoryBean;
    }
}
