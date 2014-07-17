package com.site.quartz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zdsoft on 14-7-10.
 */
public class DataWorkContext {

    private static Map<String, ScheduleJob> jobMap = new HashMap<String, ScheduleJob>();

    static {
        for (int i = 0; i < 5; i++) {
            ScheduleJob job = new ScheduleJob();
            job.setJobId("10001" + i);
            job.setJobName("data_import" + i);
            job.setJobGroup("dataWork");
            job.setJobStatus("1");
            job.setCronExpression("0/5 * * * * ?");
            job.setDesc("数据导入任务");
            addJob(job);
        }
    }

    public static void addJob(ScheduleJob scheduleJob){
        jobMap.put(scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName(), scheduleJob);
    }

    public static List<ScheduleJob> getAllJob(){
        List<ScheduleJob> list = new ArrayList<ScheduleJob>();
        for (Map.Entry<String, ScheduleJob> entry: jobMap.entrySet()){
            ScheduleJob s = entry.getValue();
            list.add(s);
        }
        return list;
    }
}
