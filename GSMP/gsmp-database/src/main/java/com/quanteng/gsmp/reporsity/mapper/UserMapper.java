/**
 * 文 件 名:  UserMapper
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/13 0013
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.mapper;

import com.quanteng.gsmp.reporsity.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/9/13 0013
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface UserMapper {

    /**
     * 新增
     *
     * @param user
     */
    void add(User user);

    /**
     * 修改
     *
     * @param user
     */
    void modify(User user);

    /**
     * 删除
     *
     * @param uId
     */
    void delete(String uId);

    /**
     * 根据uId查询
     *
     * @param uId
     * @return
     */
    User queryById(String uId);

    /**
     * 根据条件查询
     *
     * @param params
     * @return
     */
    List<User> query(Map<String, Object> params);
}