/**
 * 文 件 名:  GetClickRecordReq
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/19 0019
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.click.request;

import com.quanteng.gsmp.commom.core.basicmodule.BasicRequset;
import lombok.Getter;
import lombok.Setter;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/10/19 0019
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class GetClickRecordReq extends BasicRequset {

    private String appName;

    private String channel;

    private String subChannel;

    private String thirdSubChannel;

    private String startTime;

    private String endTime;

    @Override
    public String toString() {
        return "GetClickRecordReq{" +
                "appName='" + appName + '\'' +
                ", channel='" + channel + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
