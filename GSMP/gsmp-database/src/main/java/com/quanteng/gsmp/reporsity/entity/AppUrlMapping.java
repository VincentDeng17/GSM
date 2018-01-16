/**
 * 文 件 名:  AppUrlMapping
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/9/11 0011
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
 * <应用URL映射关系entity实体类>
 *
 * @author dyc
 * @version 2017/9/11 0011
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class AppUrlMapping implements Serializable {

    private static final long serialVersionUID = 7050144402922957249L;

    private String mId;
    private String appName;
    private String hwUrl;
    private String clickId;
    private String subChannel;
    private String thirdSubChannel;
    private BigDecimal price;
    //    private String channelUrl;
    private String promotionalUrl;
    //    private String linkId;
    private Date insertTime;

    @Override
    public String toString() {
        return "AppUrlMapping{" +
                "mId='" + mId + '\'' +
                ", appName='" + appName + '\'' +
                ", hwUrl='" + hwUrl + '\'' +
                ", clickId='" + clickId + '\'' +
                ", subChannel='" + subChannel + '\'' +
                ", thirdSubChannel='" + thirdSubChannel + '\'' +
                ", price=" + price +
                ", promotionalUrl='" + promotionalUrl + '\'' +
                ", insertTime=" + insertTime +
                '}';
    }
}
