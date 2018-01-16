/**
 * 文 件 名:  ChannelMapper
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/27 0027
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsms.reporsity.mapper;

import com.quanteng.gsms.reporsity.entity.Channel;

import java.util.List;
import java.util.Map;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/9/27 0027
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ChannelMapper {

    void add(Channel channel);

    void modify(Channel channel);

    void delete(String channelId);

    List<Channel> query(Map<String, Object> params);

    Channel queryById(String channelId);

    int count(Map<String, Object> params);
}