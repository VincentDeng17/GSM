/**
 * 文 件 名:  DownloadDetails
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/20 0020
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.download.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <下载详情>
 *
 * @author dyc
 * @version 2017/9/20 0020
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class DownloadDetails implements Serializable {

    private static final long serialVersionUID = 8283656273407693240L;

    private String mId;
    private String appName;
    private String channel;
    private String subChannel;
    private String thirdSubChannel;
    private String linkId;
    private String subNotifyStatus;
    private String subNotifyResult;
    private String notifyTime;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    @Override
    public String toString() {
        return "DownloadDetails{" +
                "mId='" + mId + '\'' +
                ", appName='" + appName + '\'' +
                ", channel='" + channel + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                ", linkId='" + linkId + '\'' +
                ", subNotifyStatus='" + subNotifyStatus + '\'' +
                ", subNotifyResult='" + subNotifyResult + '\'' +
                ", notifyTime='" + notifyTime + '\'' +
                '}';
    }
}
