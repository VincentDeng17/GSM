/**
 * 文 件 名:  AppConfigMapper
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/6 0006
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.mapper;

import com.quanteng.gsmp.reporsity.entity.AppConfig;

import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/11/6 0006
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface AppConfigMapper {
    /**
     * 新增
     *
     * @param appConfig
     */
    void add(AppConfig appConfig);

    /**
     * 修改
     *
     * @param appConfig
     */
    void modify(AppConfig appConfig);

    /**
     * 根据mId删除
     *
     * @param configId
     */
    void delete(String configId);

    /**
     * 根据条件查询List
     *
     * @param params
     * @return
     */
    List<AppConfig> query(Map<String, Object> params);

    /**
     * 根据配置ID查询
     *
     * @param configId
     * @return
     */
    AppConfig queryByConfigId(String configId);

    /**
     * 查询总数
     *
     * @param params
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * 批量删除
     *
     * @param configIdList ID列表
     */
    void batchDelete(List<String> configIdList);
}