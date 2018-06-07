package zbl.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zbl.study.task.TestTask;
import zbl.study.utils.SchedulingUtil;

import javax.inject.Inject;
import java.time.LocalDateTime;

@SpringBootApplication
@RestController
public class Application {
    @Inject
    private SchedulingUtil scheduleUtil;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("application service started !!!");
    }

    @RequestMapping("/test.do")
    public String test() {
        scheduleUtil.addTimeTaskSchedule(new TestTask(8080, LocalDateTime.now().plusSeconds(11)));
        return "quartz success";
    }
}
