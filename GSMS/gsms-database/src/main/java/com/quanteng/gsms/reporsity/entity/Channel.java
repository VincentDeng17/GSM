/**
 * 文 件 名:  Channel
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/27 0027
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsms.reporsity.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <渠道>
 *
 * @author dyc
 * @version 2017/9/27 0027
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class Channel implements Serializable {

    private static final long serialVersionUID = -1651793103804137746L;

    private String channelId;

    private String channelName;

    private String level;

    private String remarks;

    private String notifyUrl;

    private String callbackParams;

    private Date createTime;

    @Override
    public String toString() {
        return "Channel{" +
                "channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", level='" + level + '\'' +
                ", remarks='" + remarks + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", callbackParams='" + callbackParams + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
