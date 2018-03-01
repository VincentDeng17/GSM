/**
 * 文 件 名:  ScheduledTasks
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/13 0013
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.task;

import com.quanteng.gsmp.resource.task.TaskResource;
<<<<<<< HEAD
import com.quanteng.gsmp.resource.task.request.CHReq;
=======
>>>>>>> 7471c5ec7df3c08f281e3684a78bccf89b489c99
import com.quanteng.gsmp.resource.task.request.CSReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/11/13 0013
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
public class ScheduledTasks {


    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat datefaormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private TaskResource taskResource;


    /**
     * 定时将download表的数据同步到t_conversion表，并补全通知状态
     * 十分钟
     */
    @Scheduled(initialDelay = 35000, fixedRate = 600000)
    public void conversionHandleTask() {
        LOGGER.debug(String.format("[ScheduledTasks].[conversionHandle]---->[Begin]:{}" + datefaormat.format(new Date())));
        String opType = "0";
<<<<<<< HEAD
        CHReq req = new CHReq();
        req.setOpType(opType);
        taskResource.conversionHandle(req);
=======
        taskResource.conversionHandle(opType);
>>>>>>> 7471c5ec7df3c08f281e3684a78bccf89b489c99
        LOGGER.debug(String.format("[ScheduledTasks].[conversionHandle]---->[End]:{}" + datefaormat.format(new Date())));
    }

    /**
     * 同步点击转化数据定时任务
     */
    @Scheduled(initialDelay = 30000, fixedRate = 1800000)
    public void conversionStatisticsTask() {
        LOGGER.debug(String.format("[ScheduledTasks].[conversionStatisticsTask]---->[Begin]:{}" + datefaormat.format(new Date())));
        String opType = "0";
        CSReq req = new CSReq();
        req.setOpType(opType);
        taskResource.conversionStatistics(req);
        LOGGER.debug(String.format("[ScheduledTasks].[conversionStatisticsTask]---->[End]:{}" + datefaormat.format(new Date())));
    }
}
