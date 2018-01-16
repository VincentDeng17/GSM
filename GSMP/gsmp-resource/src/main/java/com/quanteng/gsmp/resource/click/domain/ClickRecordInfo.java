/**
 * 文 件 名:  ClickRecordInfo
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/19 0019
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.click.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
public class ClickRecordInfo implements Serializable {

    private static final long serialVersionUID = 1382435965766489750L;

    private String clickId;
    private String appName;
    private String subClickId;
    private String channel;
    private String subChannel;
    private String thirdSubChannel;
    private String subReqParams;
    private String channelUrl;
    private String clickTime;

    @Override
    public String toString() {
        return "ClickRecordInfo{" +
                "clickId='" + clickId + '\'' +
                ", appName='" + appName + '\'' +
                ", subClickId='" + subClickId + '\'' +
                ", channel='" + channel + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                ", subReqParams='" + subReqParams + '\'' +
                ", channelUrl='" + channelUrl + '\'' +
                ", clickTime='" + clickTime + '\'' +
                '}';
    }
}
