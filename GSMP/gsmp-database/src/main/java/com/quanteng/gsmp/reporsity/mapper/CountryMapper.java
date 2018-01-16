/**
 * 文 件 名:  CountryMapper
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/21 0021
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.mapper;

import com.quanteng.gsmp.reporsity.entity.Country;

import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/10/21 0021
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface CountryMapper {

    void add(Country country);

    void modify(Country country);

    void delete(List<String> countryIdList);

    List<Country> query(Map<String, Object> params);

    Country queryById(String countryId);

    int count(Map<String, Object> params);
}