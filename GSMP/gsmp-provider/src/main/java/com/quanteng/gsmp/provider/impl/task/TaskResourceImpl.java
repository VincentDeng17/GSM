/**
 * 文 件 名:  TaskResourceImpl
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/14 0014
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.provider.impl.task;

import com.quanteng.gsmp.commom.core.annotation.Resource;
import com.quanteng.gsmp.commom.core.utils.DateManager;
import com.quanteng.gsmp.reporsity.entity.*;
import com.quanteng.gsmp.reporsity.mapper.*;
import com.quanteng.gsmp.resource.task.TaskResource;
import com.quanteng.gsmp.resource.task.request.CHReq;
import com.quanteng.gsmp.resource.task.request.CSReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * <d定时任务接口实现类> <功能详细描述>
 *
 * @author dyc
 * @version 2017/11/14 0014
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Resource
public class TaskResourceImpl implements TaskResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskResourceImpl.class);

    @Autowired
    DownloadStatisticsMapper downloadStatisticsMapper;

    @Autowired
    ClickRecordMapper clickRecordMapper;

    @Autowired
    ChannelConfMapper channelConfMapper;

    @Autowired
    ConversionMapper conversionMapper;

    @Autowired
    StatisticsMapper statisticsMapper;

    @Autowired
    CountryMapper countryMapper;

    @Autowired
    AppUrlMappingMapper appUrlMappingMapper;

    /**
     * 定时统计点击转化数据
     * opType 0-定时统计，1-手动统计
     *
     * @param req
     */
    @Override
    public void conversionStatistics(CSReq req) {
        LOGGER.debug(String.format("[TaskResourceImpl].[conversionStatistics]---->[Begin]" +
                ":{}" + DateManager.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " [CSReq]:" + req));

        try {
            String opType = req.getOpType();
            Map<String, Object> params = new HashMap();
            if ("0".equals(opType)) {
                Date startDate = DateManager.addDays(new Date(), -1);
                Date endDate = DateManager.addDays(new Date(), 1);
                String start = DateManager.dateToString(startDate, "yyyy-MM-dd") + " 00:00:00";
                String end = DateManager.dateToString(endDate, "yyyy-MM-dd") + " 00:00:00";
                params.put("startTime", start);
                params.put("endTime", end);
            } else if ("1".equals(opType)) {
                String startTime = req.getStartTime();
                String endTime = req.getEndTime();
                params.put("startTime", startTime);
                params.put("endTime", endTime);
            }

            //1、查询点击统计数据（按日统计）
            List<ClickStatistics> crList = clickRecordMapper.queryCR(params);

            //2、查询转化统计数据（按日统计）--cost一起在统计中计算了
            List<ConversionStatistics> csList = conversionMapper.queryCS(params);

            //3、合并点击、转化统计数据
            List<Statistics> statisticsListTemp = new ArrayList<>();
            List<Statistics> statisticsList = new ArrayList<>();
            if (crList.size() > 0) {
                for (int i = 0; i < crList.size(); i++) {
                    ClickStatistics cr = crList.get(i);
                    Statistics statistics = new Statistics();
                    statistics.setDimension(DateManager.strToDate(cr.getDimension(), "yyyy-MM-dd"));
                    statistics.setAppName(cr.getAppName());

                    String countryId = getCountryIdByAppName(cr.getAppName());
                    statistics.setCountryId(countryId);

                    statistics.setSubChannel(cr.getSubChannel());
                    statistics.setClicks(cr.getClicks());
                    statistics.setConversions(0);
                    statistics.setRate(new BigDecimal("0.00"));
                    statistics.setCost(new BigDecimal("0.00"));
                    statisticsListTemp.add(statistics);
                }

                for (int i = 0; i < statisticsListTemp.size(); i++) {
                    Statistics statistics = statisticsListTemp.get(i);
                    if (csList.size() > 0) {
                        statistics = setStatisticsValue(statistics, csList);
                    }
                    statisticsList.add(statistics);
                }
            }

            //4、查询最新两天的点击转化统计数据（点击转化统计表）
            List<Statistics> statisticsListQ = statisticsMapper.query(params);

            //5、比较3和4的结果，4中不同于3的数据，全部插入或更新到点击转化统计表中
            if (statisticsList.size() > 0) {
                //分为两部分，更新和新增插入
                List<Statistics> insertStatisticsList = new ArrayList<>();
                List<Statistics> updateStatisticsList = new ArrayList<>();
                for (int i = 0; i < statisticsList.size(); i++) {
                    Statistics s = statisticsList.get(i);
                    if (statisticsListQ.size() > 0) {
                        boolean isExist = judgeStatisticsExist(s, statisticsListQ);
                        if (!isExist) {
                            insertStatisticsList.add(s);
                        } else {
//                            boolean isEquals = judgeStatisticsEquals(s, statisticsListQ);
////                            if (!isEquals) {
//                                updateStatisticsList.add(s);
//                            }
                            updateStatisticsList.add(s);
                        }
                    } else {
                        insertStatisticsList.add(s);
                    }
                }
                if (insertStatisticsList.size() > 0) {
                    for (int i = 0; i < insertStatisticsList.size(); i++) {
                        statisticsMapper.add(insertStatisticsList.get(i));
                    }
                }
                if (updateStatisticsList.size() > 0) {
                    for (int i = 0; i < updateStatisticsList.size(); i++) {
                        statisticsMapper.modify(updateStatisticsList.get(i));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.debug(String.format("[TaskResourceImpl].[conversionStatistics]---->[End]:{}" + DateManager.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss")));

    }

    /**
     * 将T_DOWNLOAD_STATISTICS表的数据同步到T_CONVERSION表，并判断每一个条记录通知的状态
     * opType 0-最新数据同步，1-全量数据同步
     */
    @Override
    public void conversionHandle(CHReq req) {
        LOGGER.debug(String.format("[TaskResourceImpl].[conversionHandle]---->[Begin]:{}" + DateManager.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " [CHReq]:" + req));
        try {

            String opType = req.getOpType();

            Map<String, Object> params = new HashMap();
            if ("0".equals(opType)) {
                Date startDate = DateManager.addDays(new Date(), -1);
                Date endDate = DateManager.addDays(new Date(), 1);
                String start = DateManager.dateToString(startDate, "yyyy-MM-dd") + " 00:00:00";
                String end = DateManager.dateToString(endDate, "yyyy-MM-dd") + " 00:00:00";
                params.put("startTime", start);
                params.put("endTime", end);
            } else if ("1".equals(opType)) {
                String startTime = req.getStartTime();
                String endTime = req.getEndTime();
                params.put("startTime", startTime);
                params.put("endTime", endTime);
            }
            List<DownloadStatistics> downloadStatisticsList = downloadStatisticsMapper.queryDS(params);
            List<Conversion> conversionList = conversionMapper.query(params);

            List<Conversion> inserConversions = new ArrayList<>();
            if (downloadStatisticsList.size() > 0) {
                Map<String, Object> cParams = new HashMap();
                List<ChannelConf> channelConfs = channelConfMapper.query(cParams);

                if (conversionList.size() > 0) {
                    for (int i = 0; i < downloadStatisticsList.size(); i++) {
                        DownloadStatistics ds = downloadStatisticsList.get(i);
                        String mId = ds.getMId();
                        boolean isContainMid = isContainMId(mId, conversionList);
                        if (!isContainMid) {
                            Conversion conversion = new Conversion();
                            conversion.setConversionId(mId);
                            conversion.setAppName(ds.getAppName());
                            conversion.setChannel(ds.getChannel());
                            conversion.setThirdSubChannel(ds.getThirdSubChannel());
                            conversion.setSource(ds.getSource());
                            conversion.setLinkId(ds.getLinkId());
                            conversion.setNotifyTime(ds.getNotifyTime());
                            String subChannel = ds.getSubChannel();
                            String subNotifyResult = ds.getSubNotifyResult();
                            int notifyStatus = judgeStatus(subChannel, subNotifyResult, channelConfs);
                            conversion.setSubChannel(subChannel);
                            conversion.setSubNotifyResult(subNotifyResult);
                            conversion.setNotifyStatus(notifyStatus);
                            inserConversions.add(conversion);
                        }
                    }
                } else {
                    for (int i = 0; i < downloadStatisticsList.size(); i++) {
                        DownloadStatistics ds = downloadStatisticsList.get(i);
                        Conversion conversion = new Conversion();
                        conversion.setConversionId(ds.getMId());
                        conversion.setAppName(ds.getAppName());
                        conversion.setChannel(ds.getChannel());
                        conversion.setThirdSubChannel(ds.getThirdSubChannel());
                        conversion.setSource(ds.getSource());
                        conversion.setLinkId(ds.getLinkId());
                        conversion.setNotifyTime(ds.getNotifyTime());
                        String subChannel = ds.getSubChannel();
                        String subNotifyResult = ds.getSubNotifyResult();
                        int notifyStatus = judgeStatus(subChannel, subNotifyResult, channelConfs);
                        conversion.setSubChannel(subChannel);
                        conversion.setSubNotifyResult(subNotifyResult);
                        conversion.setNotifyStatus(notifyStatus);
                        inserConversions.add(conversion);
                    }
                }
                if (inserConversions.size() > 0) {
                    int insertCount = 0;
                    for (int i = 0; i < inserConversions.size(); i++) {
                        Conversion conversion = inserConversions.get(i);
                        if (insertCount < 2000) {
                            conversionMapper.add(conversion);
                            insertCount++;
                        } else {
                            break;
                        }
                    }
                    LOGGER.debug(String.format("[TaskResourceImpl].[conversionHandle]---->Synchronize end 。{}" + DateManager.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " [Synchronize count ]:" + insertCount));
                }
            } else {
                LOGGER.debug(String.format("[TaskResourceImpl].[conversionHandle]---->No need to handle conversions 。{}" + DateManager.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(String.format("[TaskResourceImpl].[conversionHandle]---->Timing tasks has an ERROR。{}" + DateManager.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " || " + e.getMessage()));
        }
        LOGGER.debug(String.format("[TaskResourceImpl].[conversionHandle]---->[End]:{}" + DateManager.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss")));
    }

    /**
     * 判断通知下游渠道结果的状态，0-通知成功、1-通知失败
     *
     * @param channelId
     * @param notifyResult
     * @param channelConfs
     * @return
     */
    private int judgeStatus(String channelId, String notifyResult, List<ChannelConf> channelConfs) {
        int status = 1;//默认失败
        for (int i = 0; i < channelConfs.size(); i++) {
            ChannelConf channelConf = channelConfs.get(i);
            if (channelId.equals(channelConf.getChannelId())) {
                String succesRespKey = channelConf.getSuccessRespKey();
                if (notifyResult.contains(succesRespKey)) {
                    status = 0;
                }
            }
        }
        return status;
    }

    /**
     * 判断下载详情表的数据是否同步道转化表
     *
     * @param mId
     * @param conversionList
     * @return
     */
    private boolean isContainMId(String mId, List<Conversion> conversionList) {
        boolean isContain = false;
        for (int i = 0; i < conversionList.size(); i++) {
            Conversion conversion = conversionList.get(i);
            if (mId.equals(conversion.getConversionId())) {
                isContain = true;
            }
        }
        return isContain;
    }

    /**
     * 给点击统计实体赋转化数据值
     * 20180204 优化：cost在此方法内计算，减少SQL运行错误
     *
     * @param statistics
     * @param csList
     * @return
     */
    private Statistics setStatisticsValue(Statistics statistics, List<ConversionStatistics> csList) {
        try {
            //转化率保留4位小数
            DecimalFormat df = new DecimalFormat("##.0000");
            DecimalFormat df1 = new DecimalFormat("##.00");
            for (int i = 0; i < csList.size(); i++) {
                ConversionStatistics cs = csList.get(i);
                String dimension = cs.getDimension();
                String appName = cs.getAppName();
                String subChannel = cs.getSubChannel();
                if (dimension.equals(DateManager.dateToString(statistics.getDimension(), "yyyy-MM-dd"))
                        && appName.equals(statistics.getAppName()) && subChannel.equals(statistics.getSubChannel())) {

                    statistics.setConversions(cs.getConversions());
//                    double rate = (cs.getConversions() / statistics.getClicks()) * 100;
                    double clicks = statistics.getClicks();
                    double conversions = cs.getConversions();
                    String rate = df.format((conversions / clicks) * 100);
                    statistics.setRate(new BigDecimal(rate));

                    Map<String, Object> params = new HashMap();
                    params.put("appName", appName);
                    params.put("subChannel", subChannel);
                    List<AppUrlMapping> aumList = appUrlMappingMapper.query(params);
                    BigDecimal price = aumList.get(0).getPrice();
                    double priced = price.doubleValue();
                    LOGGER.debug(String.format("[TaskResourceImpl].[setStatisticsValue]---->[priced]:{}" + priced));
                    String cost = df1.format(conversions * priced);

                    LOGGER.debug(String.format("[TaskResourceImpl].[setStatisticsValue]---->[cost]:{}" + cost));
                    statistics.setCost(new BigDecimal(cost));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOGGER.debug(String.format("[TaskResourceImpl].[setStatisticsValue]---->[statistics]:{}" + statistics.toString()));
        return statistics;
    }

    /**
     * 根据主键判断是否存在该调数据
     * 联合主键 dimension，app_name，sub_channel
     *
     * @param s
     * @param sList
     * @return
     */
    private boolean judgeStatisticsExist(Statistics s, List<Statistics> sList) {
        boolean isExist = false;
        try {
            for (int i = 0; i < sList.size(); i++) {
                Statistics s0 = sList.get(i);
                String dimension = DateManager.dateToString(s0.getDimension(), "yyyy-MM-dd");
                String appName = s0.getAppName();
                String subChannel = s0.getSubChannel();
                String dimension_s = DateManager.dateToString(s.getDimension(), "yyyy-MM-dd");
                if (dimension.equals(dimension_s) && appName.equals(s.getAppName()) && subChannel.equals(s.getSubChannel())) {
                    isExist = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExist;
    }

    /**
     * 判断数据是否相同，只需要判断点击总数是否相同即可
     *
     * @param s
     * @param sList
     * @return
     */
    private boolean judgeStatisticsEquals(Statistics s, List<Statistics> sList) {
        boolean isEquals = false;
        try {
            for (int i = 0; i < sList.size(); i++) {
                Statistics s1 = sList.get(i);
                String dimension = DateManager.dateToString(s1.getDimension(), "yyyy-MM-dd");
                String appName = s1.getAppName();
                String subChannel = s1.getSubChannel();
                int clicks = s1.getClicks();
                if (dimension.equals(DateManager.dateToString(s.getDimension(), "yyyy-MM-dd"))
                        && appName.equals(s.getAppName()) && subChannel.equals(s.getSubChannel()) && (clicks == s1.getClicks())) {
                    isEquals = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isEquals;
    }

    /**
     * 根据应用名查询对应的国家ID
     *
     * @param appName
     * @return
     */
    private String getCountryIdByAppName(String appName) {
        String countryId = "";
        String[] appNames = appName.split("-");
        String abbreviation = appNames[0];
        Map<String, Object> params = new HashMap();
        params.put("countryAbbreviation", abbreviation);
        List<Country> countries = countryMapper.query(params);
        if (countries.size() > 0) {
            Country country = countries.get(0);
            countryId = country.getCountryId();
        }
        return countryId;
    }
}
