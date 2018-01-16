/**
 * 文 件 名:  AppUrlMappingMapper
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.mapper;

import com.quanteng.gsmp.reporsity.entity.AppUrlMapping;

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
public interface AppUrlMappingMapper {
    /**
     * 新增
     *
     * @param appUrlMapping
     */
    void add(AppUrlMapping appUrlMapping);

    /**
     * 修改
     *
     * @param appUrlMapping
     */
    void modify(AppUrlMapping appUrlMapping);

    /**
     * 根据mId列表批量删除
     *
     * @param mIdList
     */
    void delete(List<String> mIdList);

    /**
     * 根据条件查询List
     *
     * @param params
     * @return
     */
    List<AppUrlMapping> query(Map<String, Object> params);

    /**
     * 根据linkId查询
     *
     * @param linkId
     * @return
     */
//    List<AppUrlMapping> queryByLinkId(String linkId);

    /**
     * 根据主键ID查询（m_id）
     *
     * @param mid
     * @return
     */
    AppUrlMapping queryByMId(String mid);

    /**
     * 根据条件查询总数
     *
     * @param params
     * @return
     */
    int getTotalByCondition(Map<String, Object> params);
}