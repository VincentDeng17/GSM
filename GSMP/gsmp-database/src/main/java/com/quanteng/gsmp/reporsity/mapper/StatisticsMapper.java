/**
 * 文 件 名:  StatisticsMapper
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/21 0021
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.mapper;

import com.quanteng.gsmp.reporsity.entity.Statistics;

import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/11/21 0021
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface StatisticsMapper {

    void add(Statistics statistics);

    void modify(Statistics statistics);

    void delete(Map<String, Object> params);

    /**
     * 查询点击转化统计表数据
     *
     * @param params
     * @return
     */
    List<Statistics> query(Map<String, Object> params);

    /**
     * 按照天查询点击转化统计表
     *
     * @param params
     * @return
     */
    List<Statistics> queryByD(Map<String, Object> params);

    int countByD(Map<String, Object> params);

    /**
     * 按照月/年查询点击转化表
     *
     * @param params
     * @return
     */
    List<Statistics> queryByMY(Map<String, Object> params);

    int countByMY(Map<String, Object> params);

}