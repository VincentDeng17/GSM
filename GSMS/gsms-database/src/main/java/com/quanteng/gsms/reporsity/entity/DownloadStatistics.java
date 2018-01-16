/**
 * 文 件 名:  DownloadStatistics
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
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
 * <下载统计entity实体类>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class DownloadStatistics implements Serializable {

    private static final long serialVersionUID = -3434700940988212026L;

    private String mId;
    private String appName;
    private String channel;
    private String subChannel;
    private String thirdSubChannel;
    private String source;
    private String linkId;
    private String subNotifyResult;
    private Date notifyTime;

    @Override
    public String toString() {
        return "DownloadStatistics{" +
                "mId='" + mId + '\'' +
                ", appName='" + appName + '\'' +
                ", channel='" + channel + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                ", source='" + source + '\'' +
                ", linkId='" + linkId + '\'' +
                ", subNotifyResult='" + subNotifyResult + '\'' +
                ", notifyTime=" + notifyTime +
                '}';
    }

}
