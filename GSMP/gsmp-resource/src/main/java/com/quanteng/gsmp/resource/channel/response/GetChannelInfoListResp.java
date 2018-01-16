/**
 * 文 件 名:  GetChannelInfoListResp
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/10 0010
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.channel.response;

import com.quanteng.gsmp.commom.core.basicmodule.BasicResult;
import com.quanteng.gsmp.resource.channel.domain.ChannelInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <查询渠道管理信息响应类>
 *
 * @author dyc
 * @version 2017/10/10 0010
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class GetChannelInfoListResp extends BasicResult {

    private List<ChannelInfo> channelInfos;

    private int total;

    @Override
    public String toString() {
        return "GetChannelInfoListResp{" +
                "channelInfos=" + channelInfos +
                ", total=" + total +
                '}';
    }
}
