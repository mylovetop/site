package com.site.quartz;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zdsoft on 14-7-10.
 */
public class DataConversionTask {

    private static final Logger log = LoggerFactory.getLogger(DataConversionTask.class);

    public void run(){
        if (log.isInfoEnabled()){
            log.info("数据转换线程任务开始");
        }
        System.out.println("数据转换线程任务开始");
    }
}
