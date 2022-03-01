package com.example.etaservice.schedule;

import com.example.etaservice.service.StatisticsDailyService;
import com.example.etaservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataUnit;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService staService;



    //每天凌晨一点执行方法将前一天的数据进行添加
    @Scheduled(cron="0 0 1 * * ?")
    public void task2(){
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
