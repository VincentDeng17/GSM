/**
 * 文 件 名:  AppInfoMapper
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/6 0006
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.mapper;

import com.quanteng.gsmp.reporsity.entity.AppInfo;

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
public interface AppInfoMapper {

    /**
     * 新增
     *
     * @param appInfo
     */
    void add(AppInfo appInfo);

    /**
     * 修改
     *
     * @param appInfo
     */
    void modify(AppInfo appInfo);

    /**
     * 根据appId删除
     *
     * @param appIdList
     */
    void delete(List<String> appIdList);

    /**
     * 根据条件查询List
     *
     * @param params
     * @return
     */
    List<AppInfo> query(Map<String, Object> params);

    /**
     * 根据应用ID查询
     *
     * @param appId
     * @return
     */
    AppInfo queryByAppId(String appId);


    /**
     * 统计总数
     *
     * @param params
     * @return
     */
    int count(Map<String, Object> params);
}