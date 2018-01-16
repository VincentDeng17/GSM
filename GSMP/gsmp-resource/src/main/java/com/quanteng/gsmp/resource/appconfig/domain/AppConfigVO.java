/**
 * 文 件 名:  AppConfigVO
 * 版    权:  Quanten Teams. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zhouhaofeng
 * 修改时间:  2017/11/6
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.quanteng.gsmp.resource.appconfig.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * app配置VO
 *
 * @author zhouhaofeng
 * @version 2017/11/6
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
@ToString
public class AppConfigVO implements Serializable
{
	private static final long serialVersionUID = -1L;

	private String configId;

	private String countryId;

	private String countryAbbreviation;

	private String serviceId;

	private String serviceName;

	private String appId;

	private String appName;

	private String originalUrl;

	private String createTime;

	private String originalAppName;
}
