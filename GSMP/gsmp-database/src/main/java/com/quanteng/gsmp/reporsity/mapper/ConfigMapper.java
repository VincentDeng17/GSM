/**
 * 文 件 名:  ConfigMapper
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/30 0030
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.mapper;

import com.quanteng.gsmp.reporsity.entity.Config;

import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/10/30 0030
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ConfigMapper {

    void add(Config config);

    void modify(Config config);

    void delete(String confId);

    List<Config> query(Map<String, Object> params);

    Config queryByName(String paramName);

    int count(Map<String, Object> params);
}