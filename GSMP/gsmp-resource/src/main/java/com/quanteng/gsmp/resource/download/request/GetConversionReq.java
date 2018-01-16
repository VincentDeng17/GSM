/**
 * 文 件 名:  GetConversionReq
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/30 0030
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.download.request;

import com.quanteng.gsmp.commom.core.basicmodule.BasicRequset;
import lombok.Getter;
import lombok.Setter;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/10/30 0030
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class GetConversionReq extends BasicRequset {

    private String appName;

    //统计时间维度
    private String dimension;

    private String country;

    private String service;

    private String subChannel;

    private String thirdSubChannel;

    private String startTime;

    private String endTime;

    //flag: 1 查询全部  其他 查询当前页
    private String flag;

    @Override
    public String toString() {
        return "GetConversionReq{" +
                "appName='" + appName + '\'' +
                ", dimension='" + dimension + '\'' +
                ", country='" + country + '\'' +
                ", service='" + service + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
