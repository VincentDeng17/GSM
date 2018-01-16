/**
 * 文 件 名:  GetDownloadInfoReq
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.download.request;

import com.quanteng.gsmp.commom.core.basicmodule.BasicRequset;
import lombok.Getter;
import lombok.Setter;

/**
 * <查询下载信息List请求类>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class GetDownloadInfoReq extends BasicRequset {

    private String appName;

    private String channel;

    private String subChannel;

    private String thirdSubChannel;

    private String startTime;

    private String endTime;

    @Override
    public String toString() {
        return "GetDownloadInfoReq{" +
                "appName='" + appName + '\'' +
                ", channel='" + channel + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
