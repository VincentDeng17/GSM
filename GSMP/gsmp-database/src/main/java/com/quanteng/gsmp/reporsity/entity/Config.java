/**
 * 文 件 名:  Config
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
import java.util.Date;

/**
 * <配置>
 *
 * @author dyc
 * @version 2017/10/30 0030
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class Config implements Serializable {

    private static final long serialVersionUID = 721610295823407393L;

    private String confId;
    private String paramName;
    private String paramValue;
    private String remarks;
    private Date createTime;

    @Override
    public String toString() {
        return "Config{" +
                "confId='" + confId + '\'' +
                ", paramName='" + paramName + '\'' +
                ", paramValue='" + paramValue + '\'' +
                ", remarks='" + remarks + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
