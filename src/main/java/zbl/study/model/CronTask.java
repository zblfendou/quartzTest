package zbl.study.model;

public abstract class CronTask extends Task {


    public abstract String getJobName();

    public abstract String getJobGroup();

    public abstract String getCondExpession();
}
