/**
 * 文 件 名:  ChannelConf
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/20 0020
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/11/20 0020
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class ChannelConf implements Serializable {

    private static final long serialVersionUID = -5173383756981704495L;

    private String channelId;

    private String response;

    private String successRespKey;

    @Override
    public String toString() {
        return "ChannelConf{" +
                "channelId='" + channelId + '\'' +
                ", response='" + response + '\'' +
                ", successRespKey='" + successRespKey + '\'' +
                '}';
    }
}
