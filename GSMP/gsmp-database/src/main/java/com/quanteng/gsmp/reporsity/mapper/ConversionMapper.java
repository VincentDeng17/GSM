/**
 * 文 件 名:  ConversionMapper
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/20 0020
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.mapper;

import com.quanteng.gsmp.reporsity.entity.Conversion;
import com.quanteng.gsmp.reporsity.entity.ConversionStatistics;

import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/11/20 0020
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ConversionMapper {

    void add(Conversion conversion);

    void modify(Conversion conversion);

    void delete(String conversionId);

    List<Conversion> query(Map<String, Object> params);

    List<Conversion> queryDetails(Map<String, Object> params);

    int getCount(Map<String, Object> params);

    List<ConversionStatistics> queryCS(Map<String, Object> params);
}