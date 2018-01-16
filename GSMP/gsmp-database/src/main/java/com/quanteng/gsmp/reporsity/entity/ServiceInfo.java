/**
 * 文 件 名:  ServiceInfo
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/10/21 0021
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <一句话功能简述> <功能详细描述>
 *
 * @author dyc
 * @version 2017/10/21 0021
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class ServiceInfo implements Serializable{

    private static final long serialVersionUID = 7465746845125617969L;

    private String serviceId;
    private String serviceCode;
    private String serviceName;
    private String remarks;
    private Date createTime;

    @Override
    public String toString() {
        return "ServiceInfo{" +
                "serviceId='" + serviceId + '\'' +
                ", serviceCode='" + serviceCode + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
