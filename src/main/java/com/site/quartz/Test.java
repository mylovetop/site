package com.site.quartz;

import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zdsoft on 14-7-10.
 */
public class Test {


    @Resource
    SchedulerFactoryBean schedulerFactoryBean;


    public void run() throws Exception{
        System.out.println("Test run");
        try {


        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        List<ScheduleJob> jobList = DataWorkContext.getAllJob();

        for (ScheduleJob job: jobList){

            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());

            //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
            CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);

            if (null == trigger){

                JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
                        .withIdentity(job.getJobName(), job.getJobGroup()).build();

                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                //按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
                        .withSchedule(scheduleBuilder).build();

                scheduler.scheduleJob(jobDetail, trigger);

            } else {

                // Trigger已存在，那么更新相应的定时设置
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                //按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder).build();

                //按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);

            }

        }

        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
