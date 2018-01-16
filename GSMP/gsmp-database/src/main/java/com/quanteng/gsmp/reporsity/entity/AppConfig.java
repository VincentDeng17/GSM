/**
 * 文 件 名:  AppConfig
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  dyc
 * 修改时间:  2017/11/6 0006
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.reporsity.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <应用配置信息> <功能详细描述>
 *
 * @author dyc
 * @version 2017/11/6 0006
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
@ToString
public class AppConfig implements Serializable {

    private static final long serialVersionUID = 7740732339033645747L;

    private String configId;
    private String countryId;
    private String countryAbbreviation;
    private String serviceId;
    private String serviceName;
    private String appId;
    private String appName;
    private String originalUrl;
    private Date createTime;
    private String originalAppName;
}
