/**
 * 文 件 名:  DownloadInfo
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.download.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <应用下载信息类>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class DownloadInfo implements Serializable {

    private static final long serialVersionUID = -1424048353088755951L;

    private String appName;
    private String channel;
    private String subChannel;
    private String thirdSubChannel;

    private long downloadTimes;

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "appName='" + appName + '\'' +
                ", channel='" + channel + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                ", downloadTimes=" + downloadTimes +
                '}';
    }

}
