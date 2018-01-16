/**
 * 文 件 名:  ConversionStatistics
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/15 0015
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/11/15 0015
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class ConversionStatistics implements Serializable {

    private static final long serialVersionUID = 5876486886448300910L;

    private String dimension;

    private String appName;

    private String subChannel;

    private int conversions;

    private BigDecimal cost;

    @Override
    public String toString() {
        return "ConversionStatistics{" +
                "dimension='" + dimension + '\'' +
                ", appName='" + appName + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", conversions=" + conversions +
                ", cost=" + cost +
                '}';
    }
}
