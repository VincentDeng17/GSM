/**
 * 文 件 名:  DownloadCount
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

/**
 * <下载历史统计Entity类>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class DownloadCount implements Serializable {

    private static final long serialVersionUID = -759979162111312711L;

    private String appName;
    private String channel;
    private String subChannel;
    private String thirdSubChannel;
    private int downloadTimes;

    @Override
    public String toString() {
        return "DownloadCount{" +
                "appName='" + appName + '\'' +
                ", channel='" + channel + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                ", downloadTimes=" + downloadTimes +
                '}';
    }
}
