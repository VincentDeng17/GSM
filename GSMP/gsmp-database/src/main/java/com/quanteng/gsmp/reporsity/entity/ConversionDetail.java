/**
 * 文 件 名:  ConversionDetail
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/30 0030
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
public class ConversionDetail implements Serializable{

    private static final long serialVersionUID = -6573070531652987304L;

    //统计的时间维度
    private String dimension;

    private String appName;

    private String channel;

    private String subChannel;

    private String thirdSubChannel;

    private String clicks;

    private String conversions;

    private String conversionsRate;

    private String cost;

    @Override
    public String toString() {
        return "ConversionDetail{" +
                "dimension='" + dimension + '\'' +
                ", appName='" + appName + '\'' +
                ", channel='" + channel + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                ", clicks='" + clicks + '\'' +
                ", conversions='" + conversions + '\'' +
                ", conversionsRate='" + conversionsRate + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
