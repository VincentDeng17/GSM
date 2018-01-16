/**
 * 文 件 名:  Statistics
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/21 0021
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <点击转化统计实体> <功能详细描述>
 *
 * @author dyc
 * @version 2017/11/21 0021
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class Statistics implements Serializable {

    private static final long serialVersionUID = -1810197328445647520L;

    private Date dimension;
    private String appName;
    private String subChannel;
    private String countryId;
    private int clicks;
    private int conversions;
    private BigDecimal rate;
    private BigDecimal cost;

    @Override
    public String toString() {
        return "Statistics{" +
                "dimension='" + dimension + '\'' +
                ", appName='" + appName + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", countryId='" + countryId + '\'' +
                ", clicks=" + clicks +
                ", conversions=" + conversions +
                ", rate=" + rate +
                ", cost=" + cost +
                '}';
    }

}
