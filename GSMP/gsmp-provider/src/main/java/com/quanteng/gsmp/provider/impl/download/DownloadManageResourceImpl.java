/**
 * 文 件 名:  DownloadManageResourceImpl
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.provider.impl.download;

import com.quanteng.gsmp.commom.core.annotation.Resource;
import com.quanteng.gsmp.commom.core.basicmodule.BasicResult;
import com.quanteng.gsmp.commom.core.constants.Constants;
import com.quanteng.gsmp.commom.core.utils.DateManager;
import com.quanteng.gsmp.provider.impl.BasicMethod;
import com.quanteng.gsmp.reporsity.entity.*;
import com.quanteng.gsmp.reporsity.mapper.*;
import com.quanteng.gsmp.resource.download.DownloadManageResource;
import com.quanteng.gsmp.resource.download.domain.Conversion;
import com.quanteng.gsmp.resource.download.domain.DownloadDetails;
import com.quanteng.gsmp.resource.download.domain.DownloadInfo;
import com.quanteng.gsmp.resource.download.request.GetConversionReq;
import com.quanteng.gsmp.resource.download.request.GetDownloadDetailsReq;
import com.quanteng.gsmp.resource.download.request.GetDownloadInfoReq;
import com.quanteng.gsmp.resource.download.response.GetConversionResp;
import com.quanteng.gsmp.resource.download.response.GetDownloadDetailsListResp;
import com.quanteng.gsmp.resource.download.response.GetDownloadInfoListResp;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Resource
public class DownloadManageResourceImpl extends BasicMethod implements DownloadManageResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadManageResourceImpl.class);

    @Autowired
    DownloadStatisticsMapper downloadStatisticsMapper;

    @Autowired
    AppUrlMappingMapper appUrlMappingMapper;

    @Autowired
    ClickRecordMapper clickRecordMapper;

    @Autowired
    ChannelMapper channelMapper;

    @Autowired
    ConfigMapper configMapper;

    @Autowired
    StatisticsMapper statisticsMapper;

    @Autowired
    ConversionMapper conversionMapper;

    @Override
    public GetDownloadInfoListResp queryDownloadInfo(HttpServletRequest request, GetDownloadInfoReq req) {
        LOGGER.debug(
                String.format("[DownloadManageResourceImpl].[queryDownloadInfo]---->[GetDownloadInfoReq]:%s", req));
        GetDownloadInfoListResp resp = new GetDownloadInfoListResp();
        List<DownloadInfo> downloadInfos = new ArrayList<>();
        //判断是否具有权限，无权限则不允许操作
        boolean hasAuth = auth(request);
        if (!hasAuth) {
            resp.setResultCode(Constants.AUTH_FAIL_CODE);
            resp.setResultMsg(Constants.AUTH_FAIL_MSG);
            LOGGER.debug("[DownloadManageResourceImpl].[queryDownloadInfo]---->ERROR![Auth is Fail!]");
            return resp;
        }

        if (null == req) {
            resp.setResultCode(Constants.FAIL_CODE);
            resp.setResultMsg(Constants.FAIL_MSG);
            resp.setDownloadInfos(downloadInfos);
            LOGGER.debug("[DownloadManageResourceImpl].[queryDownloadInfo]---->ERROR![GetDownloadInfoReq is null]");
            return resp;
        }
        if ("".equals(req.getPageIndex())) {
            req.setPageIndex(1);
        }
        if ("".equals(req.getPageSize())) {
            req.setPageSize(10);
        }

        try {
            Map<String, Object> params = new HashMap();
            if (null != req.getAppName() && !"".equals(req.getAppName())) {
                params.put("appName", req.getAppName());
            }
            if (null != req.getChannel() && !"".equals(req.getChannel())) {
                params.put("channel", req.getChannel());
            }
            if (null != req.getSubChannel() && !"".equals(req.getSubChannel())) {
                params.put("subChannel", req.getSubChannel());
            }
            if (null != req.getThirdSubChannel() && !"".equals(req.getThirdSubChannel())) {
                params.put("thirdSubChannel", req.getThirdSubChannel());
            }
            if (null != req.getStartTime() && !"".equals(req.getStartTime())) {
                params.put("startTime", req.getStartTime());
            }
            if (null != req.getEndTime() && !"".equals(req.getEndTime())) {
                params.put("endTime", req.getEndTime());
            }
            params.put("start", (req.getPageIndex() - 1) * req.getPageSize());
            params.put("psize", req.getPageSize());

            List<DownloadCount> downloadCounts = downloadStatisticsMapper.count(params);
            downloadInfos = getDownloadInfos(downloadCounts);

            int total = downloadStatisticsMapper.getTotalByCondition(params);

            resp.setResultCode(Constants.SUCCESS_CODE);
            resp.setResultMsg(Constants.SUCCESS_MSG);
            resp.setDownloadInfos(downloadInfos);
            resp.setTotal(total);
            LOGGER.debug(String.format(
                    "[DownloadManageResourceImpl].[queryDownloadInfo]---->SUCCESS![GetDownloadInfoListResp]:%s", resp));
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            resp.setResultCode(Constants.FAIL_CODE);
            resp.setResultMsg(Constants.FAIL_MSG);
            LOGGER.debug(
                    String.format("[DownloadManageResourceImpl].[queryDownloadInfo]---->ERROR!%s", e.getMessage()));
            return resp;
        }
    }

    @Override
    public GetDownloadDetailsListResp queryDownloadDetails(HttpServletRequest request, GetDownloadDetailsReq req) {
        LOGGER.debug(
                String.format("[DownloadManageResourceImpl].[queryDownloadDetails]---->[GetDownloadDetailsReq]:%s",
                        req));
        GetDownloadDetailsListResp resp = new GetDownloadDetailsListResp();
        List<DownloadDetails> downloadDetailsList = new ArrayList<>();
        //判断是否具有权限，无权限则不允许操作
        boolean hasAuth = auth(request);
        if (!hasAuth) {
            resp.setResultCode(Constants.AUTH_FAIL_CODE);
            resp.setResultMsg(Constants.AUTH_FAIL_MSG);
            LOGGER.debug("[DownloadManageResourceImpl].[queryDownloadDetails]---->ERROR![Auth is Fail!]");
            return resp;
        }

        if (null == req) {
            resp.setResultCode(Constants.FAIL_CODE);
            resp.setResultMsg(Constants.FAIL_MSG);
            LOGGER.debug(
                    "[DownloadManageResourceImpl].[queryDownloadDetails]---->ERROR![GetDownloadDetailsReq is null]");
            return resp;
        }
        if ("".equals(req.getPageIndex())) {
            req.setPageIndex(1);
        }
        if ("".equals(req.getPageSize())) {
            req.setPageSize(10);
        }

        try {
            Map<String, Object> params = new HashMap();
            if (null != req.getAppName() && !"".equals(req.getAppName())) {
                params.put("appName", req.getAppName());
            }
            if (null != req.getSubChannel() && !"".equals(req.getSubChannel())) {
                params.put("subChannel", req.getSubChannel());
            }
            if (null != req.getLinkId() && !"".equals(req.getLinkId())) {
                params.put("linkId", req.getLinkId());
            }
            if (null != req.getNotifyStatus() && !"".equals(req.getNotifyStatus())) {
                params.put("notifyStatus", req.getNotifyStatus());
            }
            if (null != req.getStartTime() && !"".equals(req.getStartTime())) {
                params.put("startTime", req.getStartTime());
            }
            if (null != req.getEndTime() && !"".equals(req.getEndTime())) {
                params.put("endTime", req.getEndTime());
            }
            params.put("start", (req.getPageIndex() - 1) * req.getPageSize());
            params.put("psize", req.getPageSize());

//            List<DownloadStatistics> downloadStatistics = downloadStatisticsMapper.query(params);
//            downloadDetailsList = getDownloadDetailsList(downloadStatistics);
//
//            int total = downloadStatisticsMapper.getDetailsTotalByCondition(params);

            List<com.quanteng.gsmp.reporsity.entity.Conversion> conversionList = conversionMapper.queryDetails(params);
            downloadDetailsList = getConversionDetails(conversionList);
            int total = conversionMapper.getCount(params);

            resp.setResultCode(Constants.SUCCESS_CODE);
            resp.setResultMsg(Constants.SUCCESS_MSG);
            resp.setDownloadDetailsList(downloadDetailsList);
            resp.setTotal(total);
            LOGGER.debug(String.format(
                    "[DownloadManageResourceImpl].[queryDownloadDetails]---->SUCCESS![GetDownloadDetailsListResp]:%s",
                    resp));
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            resp.setResultCode(Constants.FAIL_CODE);
            resp.setResultMsg(Constants.FAIL_MSG);
            LOGGER.debug(String.format("[DownloadManageResourceImpl].[queryDownloadDetails]---->ERROR!%s",
                    e.getMessage()));
            return resp;
        }
    }

    /*
        @Override
        public GetConversionResp queryConversion(HttpServletRequest request, GetConversionReq req) {
            LOGGER.debug(String.format("[DownloadManageResourceImpl].[queryConversion]---->[GetConversionReq]:%s", req));
            GetConversionResp resp = new GetConversionResp();
            List<Conversion> conversionList = new ArrayList<>();
            //判断是否具有权限，无权限则不允许操作
            boolean hasAuth = auth(request);
            if (!hasAuth) {
                resp.setResultCode(Constants.AUTH_FAIL_CODE);
                resp.setResultMsg(Constants.AUTH_FAIL_MSG);
                LOGGER.debug("[DownloadManageResourceImpl].[queryConversion]---->ERROR![Auth is Fail!]");
                return resp;
            }

            if (null == req) {
                resp.setResultCode(Constants.FAIL_CODE);
                resp.setResultMsg(Constants.FAIL_MSG);
                LOGGER.debug("[DownloadManageResourceImpl].[queryConversion]---->ERROR![GetConversionReq is null]");
                return resp;
            }
            if ("".equals(req.getPageIndex())) {
                req.setPageIndex(1);
            }
            if ("".equals(req.getPageSize())) {
                req.setPageSize(10);
            }

            try {
                Map<String, Object> params = new HashMap();
                if (null != req.getAppName() && !"".equals(req.getAppName())) {
                    params.put("appName", req.getAppName());
                }
                if (null != req.getDimension() && !"".equals(req.getDimension())) {
                    String reqDimension = req.getDimension();
                    //默认按天统计
                    String dimension = "%Y-%m-%d";
                    Config config = configMapper.queryByName("dateFormater");
                    String paramValue = config.getParamValue();
                    String[] values = paramValue.split(",");
                    for (int i = 0; i < values.length; i++) {
                        String value = values[i];
                        String[] v = value.split("#");
                        if (reqDimension.equals(v[0])) {
                            dimension = v[1];
                        }
                    }
                    params.put("dimension", dimension);
                }
                if (null != req.getSubChannel() && !"".equals(req.getSubChannel())) {
                    params.put("subChannel", req.getSubChannel());
                }
                if (null != req.getThirdSubChannel() && !"".equals(req.getThirdSubChannel())) {
                    params.put("thirdSubChannel", req.getThirdSubChannel());
                }
                if (null != req.getStartTime() && !"".equals(req.getStartTime())) {
                    params.put("startTime", req.getStartTime());
                }
                if (null != req.getEndTime() && !"".equals(req.getEndTime())) {
                    params.put("endTime", req.getEndTime());
                }
                //1 查询全部，其他查询当前页
                if (!"1".equals(req.getFlag())) {
                    params.put("start", (req.getPageIndex() - 1) * req.getPageSize());
                    params.put("psize", req.getPageSize());
                } else {
                    params.put("start", null);
                    params.put("psize", null);
                }

                List<ConversionDetail> conversionDetails = downloadStatisticsMapper.queryConversion(params);
                conversionList = getConversionDetailList(conversionDetails);

                int total = downloadStatisticsMapper.getConversionTotal(params);

                resp.setResultCode(Constants.SUCCESS_CODE);
                resp.setResultMsg(Constants.SUCCESS_MSG);
                resp.setConversionList(conversionList);
                resp.setTotal(total);
                LOGGER.debug(
                        String.format("[DownloadManageResourceImpl].[queryConversion]---->SUCCESS![GetConversionResp]:%s",
                                resp));
                return resp;
            } catch (Exception e) {
                e.printStackTrace();
                resp.setResultCode(Constants.FAIL_CODE);
                resp.setResultMsg(Constants.FAIL_MSG);
                LOGGER.debug(
                        String.format("[DownloadManageResourceImpl].[queryConversion]---->ERROR!%s", e.getMessage()));
                return resp;
            }
        }
    */
    @Override
    public GetConversionResp queryConversion(HttpServletRequest request, GetConversionReq req) {
        LOGGER.debug(String.format("[DownloadManageResourceImpl].[queryConversion]---->[GetConversionReq]:%s", req));
        GetConversionResp resp = new GetConversionResp();
        List<Conversion> conversionList = new ArrayList<>();
        //判断是否具有权限，无权限则不允许操作
        boolean hasAuth = auth(request);
//        boolean hasAuth = true;
        if (!hasAuth) {
            resp.setResultCode(Constants.AUTH_FAIL_CODE);
            resp.setResultMsg(Constants.AUTH_FAIL_MSG);
            LOGGER.debug("[DownloadManageResourceImpl].[queryConversion]---->ERROR![Auth is Fail!]");
            return resp;
        }

        if (null == req) {
            resp.setResultCode(Constants.FAIL_CODE);
            resp.setResultMsg(Constants.FAIL_MSG);
            LOGGER.debug("[DownloadManageResourceImpl].[queryConversion]---->ERROR![GetConversionReq is null]");
            return resp;
        }
        if ("".equals(req.getPageIndex())) {
            req.setPageIndex(1);
        }
        if ("".equals(req.getPageSize())) {
            req.setPageSize(10);
        }

        try {
            String dataFormaterStr = "";
            Map<String, Object> params = new HashMap();
            if (null != req.getAppName() && !"".equals(req.getAppName())) {
                params.put("appName", req.getAppName());
            }
            if (null != req.getCountry() && !"".equals(req.getCountry())) {
                params.put("countryId", req.getCountry());
            }
            if (null != req.getDimension() && !"".equals(req.getDimension())) {
                String reqDimension = req.getDimension();
                //默认按天统计
                String dimension = "%Y-%m-%d";
                Config config = configMapper.queryByName("dateFormater");
                String paramValue = config.getParamValue();
                String[] values = paramValue.split(",");
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    String[] v = value.split("#");
                    if (reqDimension.equals(v[0])) {
                        dimension = v[1];
                    }
                }
                params.put("dimension", dimension);
            }
            if (null != req.getSubChannel() && !"".equals(req.getSubChannel())) {
                params.put("subChannel", req.getSubChannel());
            }

            if (null != req.getStartTime() && !"".equals(req.getStartTime())) {
                params.put("startTime", req.getStartTime());
            }
            if (null != req.getEndTime() && !"".equals(req.getEndTime())) {
                params.put("endTime", req.getEndTime());
            }
            //1 查询全部，其他查询当前页
            if (!"1".equals(req.getFlag())) {
                params.put("start", (req.getPageIndex() - 1) * req.getPageSize());
                params.put("psize", req.getPageSize());
            } else {
                params.put("start", null);
                params.put("psize", null);
            }

            List<Statistics> statisticses = new ArrayList<>();
            int total = 0;
            if ("D".equals(req.getDimension())) {
                statisticses = statisticsMapper.queryByD(params);
                total = statisticsMapper.countByD(params);
                dataFormaterStr = "yyyy-MM-dd";
            } else if ("M".equals(req.getDimension()) || "Y".equals(req.getDimension())) {
                statisticses = statisticsMapper.queryByMY(params);
                total = statisticsMapper.countByMY(params);
                if ("M".equals(req.getDimension())) {
                    dataFormaterStr = "yyyy-MM";
                } else if ("Y".equals(req.getDimension())) {
                    dataFormaterStr = "yyyy";
                }
            }


//            List<ConversionDetail> conversionDetails = downloadStatisticsMapper.queryConversion(params);
            conversionList = getConversionDetailList1(statisticses, dataFormaterStr);

//            int total = downloadStatisticsMapper.getConversionTotal(params);

            resp.setResultCode(Constants.SUCCESS_CODE);
            resp.setResultMsg(Constants.SUCCESS_MSG);
            resp.setConversionList(conversionList);
            resp.setTotal(total);
            LOGGER.debug(
                    String.format("[DownloadManageResourceImpl].[queryConversion]---->SUCCESS![GetConversionResp]:%s",
                            resp));
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            resp.setResultCode(Constants.FAIL_CODE);
            resp.setResultMsg(Constants.FAIL_MSG);
            LOGGER.debug(
                    String.format("[DownloadManageResourceImpl].[queryConversion]---->ERROR!%s", e.getMessage()));
            return resp;
        }
    }

    /**
     * 将entity转换成响应的实体类
     *
     * @param downloadCountList
     * @return
     */
    private List<DownloadInfo> getDownloadInfos(List<DownloadCount> downloadCountList) {
        List<DownloadInfo> downloadInfoList = new ArrayList<>();
        if (downloadCountList.size() > 0) {
            for (int i = 0; i < downloadCountList.size(); i++) {
                DownloadCount downloadCount = downloadCountList.get(i);
                DownloadInfo downloadInfo = new DownloadInfo();
                downloadInfo.setAppName(downloadCount.getAppName());
                downloadInfo.setChannel(downloadCount.getChannel());
                downloadInfo.setSubChannel(downloadCount.getSubChannel());
                downloadInfo.setThirdSubChannel(downloadCount.getThirdSubChannel());
                downloadInfo.setDownloadTimes(downloadCount.getDownloadTimes());
                downloadInfoList.add(downloadInfo);
            }
        }
        return downloadInfoList;
    }

    /**
     * 将entity转换成响应的实体类
     *
     * @param downloadStatisticses
     * @return
     */
    /*private List<DownloadDetails> getDownloadDetailsList(List<DownloadStatistics> downloadStatisticses) {
        List<DownloadDetails> downloadDetailsList = new ArrayList<>();
        if (downloadStatisticses.size() > 0) {
            for (int i = 0; i < downloadStatisticses.size(); i++) {
                DownloadStatistics downloadStatistics = downloadStatisticses.get(i);
                DownloadDetails downloadDetails = new DownloadDetails();
                downloadDetails.setmId(downloadStatistics.getMId());
                downloadDetails.setAppName(downloadStatistics.getAppName());
                downloadDetails.setChannel(downloadStatistics.getChannel());
                downloadDetails.setSubChannel(downloadStatistics.getSubChannel());
                downloadDetails.setThirdSubChannel(downloadStatistics.getThirdSubChannel());
                downloadDetails.setLinkId(downloadStatistics.getLinkId());
                downloadDetails.setSubNotifyResult(downloadStatistics.getSubNotifyResult());
                downloadDetails.setNotifyTime(
                        DateManager.dateToString(downloadStatistics.getNotifyTime(), "yyyy-MM-dd HH:mm:ss"));
                downloadDetailsList.add(downloadDetails);
            }
        }
        return downloadDetailsList;
    }*/

    /**
     * 转化详情信息响应
     *
     * @param csList
     * @return
     */
    private List<DownloadDetails> getConversionDetails(List<com.quanteng.gsmp.reporsity.entity.Conversion> csList) {
        List<DownloadDetails> downloadDetailsList = new ArrayList<>();
        if (csList.size() > 0) {
            for (int i = 0; i < csList.size(); i++) {
                com.quanteng.gsmp.reporsity.entity.Conversion cs = csList.get(i);
                DownloadDetails downloadDetails = new DownloadDetails();
                downloadDetails.setmId(cs.getConversionId());
                downloadDetails.setAppName(cs.getAppName());
                downloadDetails.setChannel(cs.getChannel());
                downloadDetails.setSubChannel(cs.getSubChannel());
                downloadDetails.setThirdSubChannel(cs.getThirdSubChannel());
                downloadDetails.setLinkId(cs.getLinkId());
                if (cs.getNotifyStatus() == 0) {
                    downloadDetails.setNotifyTime("通知成功");
                } else if (cs.getNotifyStatus() == 1) {
                    downloadDetails.setNotifyTime("通知失败");
                }
                downloadDetails.setSubNotifyResult(cs.getSubNotifyResult());
                downloadDetails.setNotifyTime(
                        DateManager.dateToString(cs.getNotifyTime(), "yyyy-MM-dd HH:mm:ss"));
                downloadDetailsList.add(downloadDetails);
            }
        }
        return downloadDetailsList;
    }

    @Override
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {

        GetConversionReq req = new GetConversionReq();

        req.setAppName(request.getParameter("appName"));
        req.setSubChannel(request.getParameter("subChannel"));
        req.setDimension(request.getParameter("dimension"));
        req.setStartTime(request.getParameter("startTime"));
        req.setEndTime(request.getParameter("endTime"));
        req.setPageIndex(Integer.valueOf(request.getParameter("pageIndex")));
        req.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
        req.setFlag(request.getParameter("flag"));

        LOGGER.debug(String.format("[DownloadManageResourceImpl].[exportExcel]---->[ExportExcelReq]:", req));

        BasicResult resp = new BasicResult();

        //查询转换记录
        GetConversionResp getConversionResp = queryConversion(request, req);

        //生成excel表
        HSSFWorkbook workbook = createExcel(getConversionResp.getConversionList());

        //文件名为日期+conversion_record.xls
        String fileName = new Date().getTime() + "_conversion_record.xls";

        //文件类型定义为excel
        response.setContentType("application/vnd.ms-excel");
        //attachment:文件作为附件下载
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);

        OutputStream outputStream = null;

        try {

            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            resp.setResultCode("-1");
            resp.setResultMsg("Write Document Exception !");
            LOGGER.debug("[DownloadManageResourceImpl].[exportExcel]---->ERROR!" + e);
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
                LOGGER.debug("[DownloadManageResourceImpl].[exportExcel]---->ERROR!" + e);
            }

        }

        LOGGER.debug(String.format("[DownloadManageResourceImpl].[exportExcel]---->[BasicResult]:", resp));

    }

    /**
     * 转化统计响应
     *
     * @param conversionDetails
     * @return
     */
    private List<Conversion> getConversionDetailList(List<ConversionDetail> conversionDetails) {
        List<Conversion> conversions = new ArrayList<>();
        if (conversionDetails.size() > 0) {
            for (int i = 0; i < conversionDetails.size(); i++) {
                ConversionDetail cd = conversionDetails.get(i);
                Conversion conversion = new Conversion();
                conversion.setDimension(cd.getDimension());
                conversion.setAppName(cd.getAppName());
                conversion.setChannel(cd.getChannel());
                conversion.setSubChannel(cd.getSubChannel());
                conversion.setThirdSubChannel(cd.getThirdSubChannel());
                conversion.setClicks(cd.getClicks());
                conversion.setConversions(cd.getConversions());
                conversion.setConversionsRate(cd.getConversionsRate());
                conversion.setCost(cd.getCost());
                conversions.add(conversion);
            }
        }
        return conversions;
    }

    /**
     * 新点击转化详情
     *
     * @param statisticsList
     * @param dfs
     * @return
     */
    private List<Conversion> getConversionDetailList1(List<Statistics> statisticsList, String dfs) {
        List<Conversion> conversions = new ArrayList<>();
        if (statisticsList.size() > 0) {
            for (int i = 0; i < statisticsList.size(); i++) {
                Statistics cs = statisticsList.get(i);
                Conversion conversion = new Conversion();
                conversion.setDimension(DateManager.dateToString(cs.getDimension(), dfs));
                conversion.setAppName(cs.getAppName());
                conversion.setChannel("Quanten");
                conversion.setSubChannel(cs.getSubChannel());
                conversion.setClicks(String.valueOf(cs.getClicks()));
                conversion.setConversions(String.valueOf(cs.getConversions()));
                String rate = cs.getRate() == null ? "0.0000" : cs.getRate().toString();
                conversion.setConversionsRate(rate);
//                conversion.setConversionsRate(cs.getRate().toString());
                conversion.setCost(cs.getCost().toString());
                conversions.add(conversion);
            }
        }
        return conversions;
    }

    private HSSFWorkbook createExcel(List<Conversion> conversionList) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        workbook.createSheet();

        HSSFSheet sheet = workbook.getSheetAt(0);

        sheet.setColumnWidth(0, 13 * 256);
        sheet.setColumnWidth(1, 26 * 256);

        //生成表结构 头部信息
        HSSFRow tb_head = sheet.createRow(0);
        tb_head.createCell(0).setCellValue("Date");
        tb_head.createCell(1).setCellValue("应用名称");
        tb_head.createCell(2).setCellValue("一级渠道");
        tb_head.createCell(3).setCellValue("二级渠道");
        tb_head.createCell(4).setCellValue("点击");
        tb_head.createCell(5).setCellValue("转化");
        tb_head.createCell(6).setCellValue("转化率（%）");
        tb_head.createCell(7).setCellValue("结算（单位：$）");

        HSSFRow row = null;

        Conversion conversion = null;

        if (CollectionUtils.isEmpty(conversionList)) {
            return workbook;
        }

        for (int i = 0; i < conversionList.size(); i++) {
            conversion = conversionList.get(i);

            row = sheet.createRow(i + 1);

            row.createCell(0).setCellValue(conversion.getDimension());
            row.createCell(1).setCellValue(conversion.getAppName());
            row.createCell(2).setCellValue(conversion.getChannel());
            row.createCell(3).setCellValue(conversion.getSubChannel());
            row.createCell(4).setCellValue(conversion.getClicks());
            row.createCell(5).setCellValue(conversion.getConversions());
            row.createCell(6).setCellValue(conversion.getConversionsRate());
            row.createCell(7).setCellValue(conversion.getCost());
        }

        return workbook;
    }
}
