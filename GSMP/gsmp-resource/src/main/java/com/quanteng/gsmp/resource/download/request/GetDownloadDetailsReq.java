/**
 * 文 件 名:  GetDownloadDetailsReq
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/20 0020
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.download.request;

import com.quanteng.gsmp.commom.core.basicmodule.BasicRequset;
import lombok.Getter;
import lombok.Setter;

/**
 * <查询下载详情请求>
 *
 * @author dyc
 * @version 2017/9/20 0020
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class GetDownloadDetailsReq extends BasicRequset {

    private String appName;

    private String subChannel;

    //泉腾记录的点击ID
    private String linkId;

    //泉腾通知下游结果状态
    private String notifyStatus;

    private String startTime;

    private String endTime;

    @Override
    public String toString() {
        return "GetDownloadDetailsReq{" +
                "appName='" + appName + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", linkId='" + linkId + '\'' +
                ", notifyStatus='" + notifyStatus + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
