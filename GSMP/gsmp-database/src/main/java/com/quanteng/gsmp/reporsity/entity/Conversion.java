/**
 * 文 件 名:  Conversion
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
import java.util.Date;

/**
 * <转化详情信息-带通知状态> <功能详细描述>
 *
 * @author dyc
 * @version 2017/11/20 0020
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class Conversion implements Serializable {

    private static final long serialVersionUID = -8572738449805647596L;

    private String conversionId;

    private String appName;

    private String channel;

    private String subChannel;

    private String thirdSubChannel;

    private String source;

    private String linkId;

    private int notifyStatus;

    private String subNotifyResult;

    private Date notifyTime;

    @Override
    public String toString() {
        return "Conversion{" +
                "conversionId='" + conversionId + '\'' +
                ", appName='" + appName + '\'' +
                ", channel='" + channel + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                ", source='" + source + '\'' +
                ", linkId='" + linkId + '\'' +
                ", notifyStatus=" + notifyStatus +
                ", subNotifyResult='" + subNotifyResult + '\'' +
                ", notifyTime=" + notifyTime +
                '}';
    }
}
