/**
 * 文 件 名:  DownloadStatisticsMapper
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.mapper;

import com.quanteng.gsmp.reporsity.entity.ConversionDetail;
import com.quanteng.gsmp.reporsity.entity.ConversionStatistics;
import com.quanteng.gsmp.reporsity.entity.DownloadCount;
import com.quanteng.gsmp.reporsity.entity.DownloadStatistics;

import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface DownloadStatisticsMapper {

    /**
     * 新增
     *
     * @param downloadStatistics
     */
    void add(DownloadStatistics downloadStatistics);

    /**
     * 修改
     *
     * @param downloadStatistics
     */
    void modify(DownloadStatistics downloadStatistics);

    /**
     * 根据mId删除
     *
     * @param mId
     */
    void delete(String mId);

    /**
     * 根据条件查询List
     *
     * @param params
     * @return
     */
    List<DownloadStatistics> query(Map<String, Object> params);

    /**
     * 统计下载情况
     *
     * @param params
     * @return
     */
    List<DownloadCount> count(Map<String, Object> params);

    /**
     * 根据条件查询总数
     *
     * @param params
     * @return
     */
    int getTotalByCondition(Map<String, Object> params);

    /**
     * 根据条件查询详情总数
     *
     * @param params
     * @return
     */
    int getDetailsTotalByCondition(Map<String, Object> params);

    /**
     * 查询转化统计
     *
     * @param params
     * @return
     */
    List<ConversionDetail> queryConversion(Map<String, Object> params);

    /**
     * 转化统计总条数
     *
     * @param params
     * @return
     */
    int getConversionTotal(Map<String, Object> params);

    /**
     * 查询转化统计
     *
     * @param params
     * @return
     */
    List<ConversionStatistics> queryConversions(Map<String, Object> params);


    /**
     * 查询下载
     *
     * @param params
     * @return
     */
    List<DownloadStatistics> queryDS(Map<String, Object> params);

}