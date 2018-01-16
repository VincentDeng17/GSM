/**
 * 文 件 名:  GetChannelInfoListReq
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/10 0010
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.channel.request;

import com.quanteng.gsmp.commom.core.basicmodule.BasicRequset;
import lombok.Getter;
import lombok.Setter;

/**
 * <查询渠道管理信息请求类>
 *
 * @author dyc
 * @version 2017/10/10 0010
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class GetChannelInfoListReq extends BasicRequset {

    private String channelId;
    private String channelName;
    private String level;
    private String remarks;

    @Override
    public String toString() {
        return "GetChannelInfoListReq{" +
                "channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", level='" + level + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
