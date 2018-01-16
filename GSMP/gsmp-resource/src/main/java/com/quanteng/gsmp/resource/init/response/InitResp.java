/**
 * 文 件 名:  InitResp
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/27 0027
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.init.response;

import com.quanteng.gsmp.commom.core.basicmodule.BasicResult;
import com.quanteng.gsmp.resource.channel.domain.ChannelInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <页面初始化响应>
 *
 * @author dyc
 * @version 2017/9/27 0027
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class InitResp extends BasicResult {

    private List<ChannelInfo> subChannels;

    private List<ChannelInfo> thirdSubChannels;


    @Override
    public String toString() {
        return "InitResp{" +
                "subChannels=" + subChannels +
                ", thirdSubChannels=" + thirdSubChannels +
                '}';
    }
}
