/**
 * 文 件 名:  ClickRecord
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/16 0016
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
 * <点击情况Entity实体>
 *
 * @author dyc
 * @version 2017/10/16 0016
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class ClickRecord implements Serializable {

    private static final long serialVersionUID = 740453401007359877L;

    private String clickId;
    private String appName;
    private String subClickId;
    private String channel;
    private String subChannel;
    private String thirdSubChannel;
    private String subReqParams;
    private String channelUrl;
    private Date clickTime;

    @Override
    public String toString() {
        return "ClickRecord{" +
                "clickId='" + clickId + '\'' +
                ", appName='" + appName + '\'' +
                ", subClickId='" + subClickId + '\'' +
                ", channel='" + channel + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                ", subReqParams='" + subReqParams + '\'' +
                ", channelUrl='" + channelUrl + '\'' +
                ", clickTime=" + clickTime +
                '}';
    }
}
